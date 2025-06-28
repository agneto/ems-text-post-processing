package com.neto.posts.postservice.util;

public class TextUtils {

    private TextUtils() {}

    public static String getFirstThreeSentences(String text) {
        if (text == null || text.isBlank()) return "";

        final String normalizedText = text.replaceAll("\\s+", " ").trim();

        final String[] sentences = normalizedText.split("(?<=[.!?])\\s+");

        final int limit = Math.min(sentences.length, 3);
        final StringBuilder result = new StringBuilder();

        for (int i = 0; i < limit; i++) {
            result.append(sentences[i].trim()).append(" ");
        }

        return result.toString().trim();
    }
}
