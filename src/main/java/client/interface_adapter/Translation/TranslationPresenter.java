package client.interface_adapter.translation;

import client.use_case.translate.TranslationOutputBoundary;
import client.use_case.translate.TranslationOutputData;

public class TranslationPresenter implements TranslationOutputBoundary {
    @Override
    public void presentTranslationResult(TranslationOutputData outputData) {
        // TODO: call the view to display the result after finish the implement of view.
        System.out.println(outputData.getTranslatedText()+" "+outputData.getDetectedLanguage());
    }
}
