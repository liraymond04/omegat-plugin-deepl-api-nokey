package com.liraymond04.deepl;

public class Test {

    public static void main(String[] args) {
        // Test the translation function with example inputs.
        try {
            String sourceLanguage = "English"; // Source language
            String targetLanguage = "French"; // Target language
            String textToTranslate = "Hello, how are you today?";

            // Call the translate function
            String translatedText = translate(sourceLanguage, targetLanguage, textToTranslate);

            // Print results
            System.out.println("Source Text: " + textToTranslate);
            System.out.println("Translated Text: " + translatedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Translates text from one language to another using the Deepl API logic.
     *
     * @param sourceLanguage The language of the input text.
     * @param targetLanguage The language to translate the text into.
     * @param text The text to be translated.
     * @return The translated text.
     */
    public static String translate(String sourceLanguage, String targetLanguage, String text) {
        // Abbreviate languages
        String abbreviatedSource = Utils.abbreviateLanguage(sourceLanguage);
        String abbreviatedTarget = Utils.abbreviateLanguage(targetLanguage);

        if (abbreviatedSource == null || abbreviatedTarget == null) {
            throw new IllegalArgumentException("Unsupported source or target language.");
        }

        // Mock response as actual API request is not implemented yet.
        // Replace this with the actual call to the API implementation.
        System.out.println(abbreviatedSource);
        System.out.println(abbreviatedTarget);
        try {
            return DeeplAPI.translate(abbreviatedSource, abbreviatedTarget, text);
        } catch(Exception e) {
            return e.getMessage();
        }
    }
}
