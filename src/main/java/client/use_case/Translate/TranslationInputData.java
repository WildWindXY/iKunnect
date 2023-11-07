package client.use_case.Translate;

public class TranslationInputData {
    final private String textToTranslate;

    public TranslationInputData(String textToTranslate) {
        this.textToTranslate = textToTranslate;
    }

    String getTextToTranslate() {
        return textToTranslate;
    }
}
