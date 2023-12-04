package client.use_case.translate;

public class TranslationOutputData {
    private final String translatedText;

    private final String detectedLanguage;

    // Constructor
    public TranslationOutputData(String translatedText, String detectedLanguage) {
        this.translatedText = translatedText;
        this.detectedLanguage = detectedLanguage;
    }

    // Getter
    public String getTranslatedText() {
        return translatedText;
    }

    public String getDetectedLanguage() {
        return detectedLanguage;
    }

}
