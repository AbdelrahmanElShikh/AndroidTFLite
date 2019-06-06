package com.example.android.androidtflite;

/**
 * Created by Abdel-Rahman El-Shikh on 05-Jun-19.
 */

public class Classification {
    public final String title;
    public final float confidence;

    public Classification(String title, float confidence) {
        this.title = title;
        this.confidence = confidence;
    }

    @Override
    public String toString() {
        return title + " " + String.format("(%.1f%%) ", confidence * 100.0f);
    }
}
