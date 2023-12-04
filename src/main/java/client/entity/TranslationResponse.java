package client.entity;

public class TranslationResponse {
    private final String translatedText;

    private final String detectedLanguage;

    // Constructor
    public TranslationResponse(String translatedText, String detectedLanguage) {
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