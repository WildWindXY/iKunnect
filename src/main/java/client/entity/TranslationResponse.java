package client.entity;

public class TranslationResponse {
    private String translatedText;

    // Constructor
    public TranslationResponse(String translatedText) {
        this.translatedText = translatedText;
    }

    // Getter
    public String getTranslatedText() {
        return translatedText;
    }

    // Setter
    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }
}