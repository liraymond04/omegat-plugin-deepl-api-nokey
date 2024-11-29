package com.liraymond04.deepl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Settings {
    public static final String API_URL = "https://www2.deepl.com/jsonrpc";

    public static final List<Map<String, String>> SUPPORTED_LANGUAGES = createSupportedLanguages();

    public static final long MAGIC_NUMBER = Long.parseLong("CAFEBABE", 16);

    private static List<Map<String, String>> createSupportedLanguages() {
        List<Map<String, String>> languages = new ArrayList<>();
        addLanguage(languages, "BG", "Bulgarian");
        addLanguage(languages, "ZH", "Chinese");
        addLanguage(languages, "ZH-CN", "Chinese");
        addLanguage(languages, "ZH-HK", "Chinese");
        addLanguage(languages, "ZH-TW", "Chinese");
        addLanguage(languages, "CS", "Czech");
        addLanguage(languages, "DA", "Danish");
        addLanguage(languages, "NL", "Dutch");
        addLanguage(languages, "EN", "English");
        addLanguage(languages, "ET", "Estonian");
        addLanguage(languages, "FI", "Finnish");
        addLanguage(languages, "FR", "French");
        addLanguage(languages, "DE", "German");
        addLanguage(languages, "EL", "Greek");
        addLanguage(languages, "HU", "Hungarian");
        addLanguage(languages, "IT", "Italian");
        addLanguage(languages, "JA", "Japanese");
        addLanguage(languages, "LV", "Latvian");
        addLanguage(languages, "LT", "Lithuanian");
        addLanguage(languages, "PL", "Polish");
        addLanguage(languages, "PT", "Portuguese");
        addLanguage(languages, "RO", "Romanian");
        addLanguage(languages, "RU", "Russian");
        addLanguage(languages, "SK", "Slovak");
        addLanguage(languages, "SL", "Slovenian");
        addLanguage(languages, "ES", "Spanish");
        addLanguage(languages, "SV", "Swedish");
        return languages;
    }

    private static void addLanguage(List<Map<String, String>> list, String code, String name) {
        Map<String, String> language = new HashMap<>();
        language.put("code", code);
        language.put("language", name);
        list.add(language);
    }
}
