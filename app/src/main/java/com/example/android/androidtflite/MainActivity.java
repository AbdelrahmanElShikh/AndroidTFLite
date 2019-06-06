package com.example.android.androidtflite;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.camerakit.CameraKitView;

import java.io.IOException;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    ImageView ivPreview;
    ImageView ivFinalPreview;
    TextView tvClassification;
    CameraKitView vCamera;
    Button btnTakePhoto;
    private Classifier classifier;
    public static AssetManager assetManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivPreview = findViewById(R.id.ivPreview);
        ivFinalPreview = findViewById(R.id.ivFinalPreview);
        tvClassification = findViewById(R.id.tvClassification);
        vCamera = findViewById(R.id.vCamera);
        btnTakePhoto = findViewById(R.id.btnTakePhoto);
        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTakePhoto();
            }
        });
        assetManager = getAssets();
        loadClassifier();
    }



    private void loadClassifier() {
        try {
            classifier = classifier.classifier(getAssets(), ModelConfig.MODEL_FILENAME);
        } catch (IOException e) {
            Toast.makeText(this, "MNIST model couldn't be loaded. Check logs for details.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        vCamera.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        vCamera.onResume();
    }

    @Override
    protected void onPause() {
        vCamera.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        vCamera.onStop();
        super.onStop();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        vCamera.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private void onTakePhoto() {
        vCamera.captureImage(new CameraKitView.ImageCallback() {
            @Override
            public void onImage(CameraKitView cameraKitView, byte[] picture) {
                onImageCaptured(picture);
            }
        });
    }
    private void onImageCaptured(byte[] picture) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(picture, 0, picture.length);
        Bitmap squareBitmap = ThumbnailUtils.extractThumbnail(bitmap, getScreenWidth(), getScreenWidth());
        ivPreview.setImageBitmap(squareBitmap);

        Bitmap preprocessedImage = ImageUtils.prepareImageForClassification(squareBitmap);
        ivFinalPreview.setImageBitmap(preprocessedImage);

        List<Classification> recognitions = classifier.recognizeImage(preprocessedImage);
        tvClassification.setText(recognitions.toString());
    }
    private int getScreenWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }
}
