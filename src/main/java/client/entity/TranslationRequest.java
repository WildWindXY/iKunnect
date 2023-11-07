package client.entity;

public class TranslationRequest {
    private String text;

    // Constructor
    public TranslationRequest(String text) {
        this.text = text;
    }

    // Getters

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}