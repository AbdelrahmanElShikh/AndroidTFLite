package com.example.android.androidtflite;

/**
 * Created by Abdel-Rahman El-Shikh on 05-Jun-19.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

/**
 * The most of those information can be found in MNIST.ipynb
 */
public class ModelConfig {

    //public static String MODEL_FILENAME = "mnist_model.tflite";
    public static String MODEL_FILENAME = "mobilenet_quant_v1_224.tflite";

    public static final int INPUT_IMG_SIZE_WIDTH = 28;
    public static final int INPUT_IMG_SIZE_HEIGHT = 28;
    public static final int FLOAT_TYPE_SIZE = 4;
    public static final int PIXEL_SIZE = 1;
    public static final int MODEL_INPUT_SIZE = FLOAT_TYPE_SIZE * INPUT_IMG_SIZE_WIDTH * INPUT_IMG_SIZE_HEIGHT * PIXEL_SIZE;

//    public static final List<String> OUTPUT_LABELS = Collections.unmodifiableList(
//            Arrays.asList("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"));
    public static final List<String> OUTPUT_LABELS = Collections.emptyList();

    public static final int MAX_CLASSIFICATION_RESULTS = 3;
    public static final float CLASSIFICATION_THRESHOLD = 0.1f;
    public static void readLabels(){
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(MainActivity.assetManager.open("lables.txt"), "UTF-8"));
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                OUTPUT_LABELS.add(mLine);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
