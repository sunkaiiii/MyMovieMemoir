package com.example.mymoviememoir.utils;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public final class BagOfWordsUtils {
    private static List<String> positiveWords;
    private static List<String> negativeWords;


    public static synchronized void requestGetPositiveAndNegativeData(@NonNull OnWordsLoadSuccessListener listener) {
        if (positiveWords != null && negativeWords != null) {
            listener.onLoadSuccess(positiveWords, negativeWords);
        }

        new Thread(() -> {
            try {
                List<String> pWords = new ArrayList<>();
                List<String> nWords = new ArrayList<>();
                InputStream positiveFile = GlobalContext.getInstance().getResources().getAssets().open("positive-words.txt");
                InputStream negativeFile = GlobalContext.getInstance().getResources().getAssets().open("negative-words.txt");
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(positiveFile))) {
                    pWords.add(reader.readLine());
                }
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(negativeFile))) {
                    nWords.add(reader.readLine());
                }
                new Handler(Looper.getMainLooper()).post(() -> {
                    positiveWords = pWords;
                    negativeWords = nWords;
                    listener.onLoadSuccess(positiveWords, negativeWords);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public interface OnWordsLoadSuccessListener {
        void onLoadSuccess(List<String> positiveWords, List<String> negativeWords);
    }
}
