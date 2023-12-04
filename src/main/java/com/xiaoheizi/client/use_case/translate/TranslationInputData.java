package com.xiaoheizi.client.use_case.translate;

public class TranslationInputData {
    final private String textToTranslate;

    public TranslationInputData(String textToTranslate) {
        this.textToTranslate = textToTranslate;
    }

    String getTextToTranslate() {
        return textToTranslate;
    }
}
