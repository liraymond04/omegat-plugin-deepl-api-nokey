package com.liraymond04.deepl;

import com.google.gson.*;

public class Extractors {

    public static String[] extractSplitSentences(JsonObject jsonResponse) {
        JsonArray sentences = jsonResponse
                .getAsJsonObject("result")
                .getAsJsonArray("splitted_texts")
                .get(0)
                .getAsJsonArray();

        String[] result = new String[sentences.size()];
        for (int i = 0; i < sentences.size(); i++) {
            result[i] = sentences.get(i).getAsString();
        }

        return result;
    }

    public static String extractTranslatedText(JsonObject jsonResponse) {
        JsonArray translations = jsonResponse
                .getAsJsonObject("result")
                .getAsJsonArray("translations");

        StringBuilder translatedText = new StringBuilder();

        for (JsonElement translationElement : translations) {
            JsonObject beam = translationElement
                    .getAsJsonObject()
                    .getAsJsonArray("beams")
                    .get(0)
                    .getAsJsonObject();

            translatedText.append(beam.get("postprocessed_sentence").getAsString()).append(" ");
        }

        return translatedText.toString().trim();
    }
}
