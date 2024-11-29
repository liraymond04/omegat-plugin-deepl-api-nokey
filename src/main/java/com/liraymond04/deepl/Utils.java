package com.liraymond04.deepl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {

    /**
     * Creates a dictionary mapping language codes and language names to their codes.
     *
     * @return A combined map of language abbreviations.
     */
    public static Map<String, String> createAbbreviationsDictionary() {
        Map<String, String> shortDict = new HashMap<>();
        Map<String, String> verboseDict = new HashMap<>();

        List<Map<String, String>> supportedLanguages = Settings.SUPPORTED_LANGUAGES;

        for (Map<String, String> language : supportedLanguages) {
            String code = language.get("code").toLowerCase();
            String name = language.get("language").toLowerCase();
            shortDict.put(code, code.toUpperCase());
            verboseDict.put(name, code.toUpperCase());
        }

        // Combine both dictionaries
        Map<String, String> combinedDict = new HashMap<>();
        combinedDict.putAll(shortDict);
        combinedDict.putAll(verboseDict);

        return combinedDict;
    }

    /**
     * Abbreviates a language name or code to its corresponding language code.
     *
     * @param language The language name or code to abbreviate.
     * @return The abbreviated language code, or null if not found.
     */
    public static String abbreviateLanguage(String language) {
        if (language == null || language.isEmpty()) {
            return null;
        }

        language = language.toLowerCase();
        Map<String, String> abbreviations = createAbbreviationsDictionary();
        return abbreviations.get(language);
    }

    /**
     * Reads the contents of a file and returns it as a single string.
     *
     * @param path The path to the file.
     * @return The file content as a string.
     * @throws IOException If an I/O error occurs.
     */
    public static String readFileLines(String path) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString().trim();
    }
}

