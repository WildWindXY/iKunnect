package client.interface_adapter.Translation;

import client.use_case.Translate.TranslationOutputBoundary;
import client.use_case.Translate.TranslationOutputData;

public class TranslationPresenter implements TranslationOutputBoundary {
    @Override
    public void presentTranslationResult(TranslationOutputData outputData) {
        // TODO: call the view to display the result after finish the implement of view.
        System.out.println(outputData.getTranslatedText()+" "+outputData.getDetectedLanguage());
    }
}
