package com.example.mymoviememoir.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.example.mymoviememoir.network.reponse.StatusesItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class BagOfWordsUtils {
    private static Set<String> positiveWords;
    private static Set<String> negativeWords;

    public enum Classification {
        NEGATIVE("Negative"),
        NEUTRAL("Neutral"),
        POSITIVE("Positive");
        private String name;

        Classification(String name) {
            this.name = name;
        }

        @NonNull
        @Override
        public String toString() {
            return name;
        }
    }

    public static synchronized void requestGetPositiveAndNegativeData(Context context, @NonNull OnWordsLoadSuccessListener listener) {
        if (positiveWords != null && negativeWords != null) {
            listener.onLoadSuccess(positiveWords, negativeWords);
        }
        new Thread(() -> {
            try {
                Set<String> pWords = new HashSet<>();
                Set<String> nWords = new HashSet<>();
                InputStream positiveFile = context.getResources().getAssets().open("positive-words.txt");
                InputStream negativeFile = context.getResources().getAssets().open("negative-words.txt");
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(positiveFile))) {
                    String read;
                    while ((read = reader.readLine()) != null) {
                        pWords.add(read);
                    }
                }
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(negativeFile))) {
                    String read;
                    while ((read = reader.readLine()) != null) {
                        nWords.add(read);
                    }
                }
                new Handler(Looper.getMainLooper()).post(() -> {
                    positiveWords = pWords;
                    negativeWords = nWords;
                    listener.onLoadSuccess(positiveWords, negativeWords);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static Map<Classification, ArrayList<StatusesItem>> makeStringDivision(List<StatusesItem> sentense, String movieName, Set<String> positiveWords, Set<String> negativeWords) {
        Map<Classification, ArrayList<StatusesItem>> result = new HashMap<>();
        for (Classification classification : Classification.values()) {
            result.put(classification, new ArrayList<>());
        }
        for (StatusesItem item : sentense) {
            String[] words = item.getText().split(" ");
            int value = 0;
            for (String word : words) {
                word = word.replace(movieName, "");
                if (positiveWords.contains(word)) {
                    value++;
                } else if (negativeWords.contains(word)) {
                    value--;
                }
            }
            if (value >0) {
                result.get(Classification.POSITIVE).add(item);
            } else if (value < 0) {
                result.get(Classification.NEGATIVE).add(item);
            } else {
                result.get(Classification.NEUTRAL).add(item);
            }
        }
        return result;
    }

    public interface OnWordsLoadSuccessListener {
        void onLoadSuccess(Set<String> positiveWords, Set<String> negativeWords);
    }
}
