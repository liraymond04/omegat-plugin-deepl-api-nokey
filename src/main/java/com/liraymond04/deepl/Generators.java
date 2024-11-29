package com.liraymond04.deepl;

import com.google.gson.*;

public class Generators {

    // Method to add spaces after ":" and "," outside of string literals, handling escaped quotes
    public static String formatJsonWithSpaces(String jsonString) {
        StringBuilder result = new StringBuilder();
        boolean insideString = false;
        boolean escapeNext = false; // To track whether the next character is escaped

        // Iterate through each character and add space where necessary
        for (int i = 0; i < jsonString.length(); i++) {
            char ch = jsonString.charAt(i);

            // Handle escaped quotes
            if (escapeNext) {
                result.append(ch); // Add the escaped character (e.g., \ or ")
                escapeNext = false; // Reset escape flag
            } else if (ch == '\\') {
                escapeNext = true; // Set escape flag if backslash is encountered
                result.append(ch); // Add backslash without change
            } else if (ch == '"') {
                insideString = !insideString; // Toggle string state
                result.append(ch); // Add the quote character
            } else if (!insideString && (ch == ':' || ch == ',')) {
                result.append(ch).append(' '); // Add space after ":" or ","
            } else {
                result.append(ch); // Add other characters as they are
            }
        }

        return result.toString();
    }

    public static String generateSplitSentencesRequestData(String text) {
        JsonObject request = new JsonObject();
        request.addProperty("jsonrpc", "2.0");
        request.addProperty("method", "LMT_split_into_sentences");

        JsonObject params = new JsonObject();
        JsonArray texts = new JsonArray();
        texts.add(text);
        params.add("texts", texts);

        JsonObject lang = new JsonObject();
        lang.addProperty("lang_user_selected", "auto");
        lang.add("user_preferred_langs", new JsonArray());

        params.add("lang", lang);
        request.add("params", params);
        request.addProperty("id", Settings.MAGIC_NUMBER); // Replace with your magic number

        Gson gson = new GsonBuilder().create();
        String jsonString = gson.toJson(request);

        return formatJsonWithSpaces(jsonString);
    }

    public static String generateTranslationRequestData(String sourceLang, String targetLang, String[] sentences) {
        JsonArray jobs = generateJobs(sentences);

        JsonObject params = new JsonObject();
        params.add("jobs", jobs);

        JsonObject lang = new JsonObject();
        JsonArray userPreferredLangs = new JsonArray();
        userPreferredLangs.add(targetLang);
        userPreferredLangs.add(sourceLang);
        lang.add("user_preferred_langs", userPreferredLangs);
        lang.addProperty("source_lang_computed", sourceLang);
        lang.addProperty("target_lang", targetLang);
        params.add("lang", lang);

        params.addProperty("priority", 1);
        params.add("commonJobParams", new JsonObject()); 
        params.addProperty("timestamp", Hacks.generateTimestamp(sentences));

        Gson gson = new GsonBuilder().create();

        // Convert JsonObject to a compact JSON string
        String jsonString = gson.toJson(createJSONRPCRequest("LMT_handle_jobs", params));

        return formatJsonWithSpaces(jsonString);
    }

    private static JsonArray generateJobs(String[] sentences) {
        JsonArray jobs = new JsonArray();
        for (int i = 0; i < sentences.length; i++) {
            JsonObject job = new JsonObject();
            job.addProperty("kind", "default");
            job.addProperty("raw_en_sentence", sentences[i]);

            JsonArray rawEnContextBefore = new JsonArray();
            for (int j = 0; j < i; j++) {
                rawEnContextBefore.add(sentences[j]);
            }
            job.add("raw_en_context_before", rawEnContextBefore);

            JsonArray rawEnContextAfter = new JsonArray();
            if (i + 1 < sentences.length) {
                rawEnContextAfter.add(sentences[i + 1]);
            }
            job.add("raw_en_context_after", rawEnContextAfter);

            job.addProperty("preferred_num_beams", 1);
            jobs.add(job);
        }
        return jobs;
    }

    private static JsonObject createJSONRPCRequest(String method, JsonObject params) {
        JsonObject request = new JsonObject();
        request.addProperty("jsonrpc", "2.0");
        request.addProperty("method", method);
        request.add("params", params);
        request.addProperty("id", Hacks.generateId());
        return request;
    }
}
