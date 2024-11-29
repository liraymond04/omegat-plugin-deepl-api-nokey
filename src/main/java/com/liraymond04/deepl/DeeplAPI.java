package com.liraymond04.deepl;

import java.net.*;
import java.io.*;
import com.google.gson.*;

public class DeeplAPI {

    public static String[] splitIntoSentences(String text) throws Exception {
        String data = Generators.generateSplitSentencesRequestData(text);
        JsonObject response = sendPostRequest(Settings.API_URL, data);
        return Extractors.extractSplitSentences(response);
    }

    public static JsonObject requestTranslation(String sourceLang, String targetLang, String text) throws Exception {
        String[] sentences = splitIntoSentences(text);
        String data = Generators.generateTranslationRequestData(sourceLang, targetLang, sentences);
        JsonObject response = sendPostRequest(Settings.API_URL, data);
        return response;
    }

    public static String translate(String sourceLang, String targetLang, String text) throws Exception {
        sourceLang = Utils.abbreviateLanguage(sourceLang);
        targetLang = Utils.abbreviateLanguage(targetLang);

        JsonObject response = requestTranslation(sourceLang, targetLang, text);
        return Extractors.extractTranslatedText(response);
    }

    private static JsonObject sendPostRequest(String url, String data) throws Exception {
        URI uri = new URI(url);
        URL obj = uri.toURL();
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("accept", "*/*");
        con.setRequestProperty("accept-language", "en-US;q=0.8,en;q=0.7");
        con.setRequestProperty("authority", "www2.deepl.com");
        con.setRequestProperty("content-type", "application/json");
        con.setRequestProperty("origin", "https://www.deepl.com");
        con.setRequestProperty("referer", "https://www.deepl.com/translator");
        con.setRequestProperty("sec-fetch-dest", "empty");
        con.setRequestProperty("sec-fetch-mode", "cors");
        con.setRequestProperty("sec-fetch-site", "same-site");
        con.setRequestProperty("user-agent",
                "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) " +
                        "Chrome/83.0.4103.97 Mobile Safari/537.36");

        System.out.println(con.getRequestProperties());
        System.out.println(url);
        System.out.println(data);

        con.setDoOutput(true);
        con.connect();
        try (OutputStream os = con.getOutputStream()) {
            os.write(data.getBytes());
        }

        int responseCode = con.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("HTTP request failed with code: " + responseCode);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return JsonParser.parseString(response.toString()).getAsJsonObject();
    }
}
