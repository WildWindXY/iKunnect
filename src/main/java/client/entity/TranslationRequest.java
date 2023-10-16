package client.entity;

public class TranslationRequest {
    private String sourceLang;
    private String targetLang;
    private String text;

    // Constructor
    public TranslationRequest(String sourceLang, String targetLang, String text) {
        this.sourceLang = sourceLang;
        this.targetLang = targetLang;
        this.text = text;
    }

    // Getters
    public String getSourceLang() {
        return sourceLang;
    }

    public String getTargetLang() {
        return targetLang;
    }

    public String getText() {
        return text;
    }

    // Setters
    public void setSourceLang(String sourceLang) {
        this.sourceLang = sourceLang;
    }

    public void setTargetLang(String targetLang) {
        this.targetLang = targetLang;
    }

    public void setText(String text) {
        this.text = text;
    }
}