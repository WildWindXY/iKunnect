package client.use_case.translate;

public class TranslationInteractor implements TranslationInputBoundary{
    private final TranslateDataAccessInterface translationDataAccessObject;

    public TranslationInteractor(TranslateDataAccessInterface translationDataAccessObject) {
        this.translationDataAccessObject = translationDataAccessObject;
    }

    @Override
    public String execute(TranslationInputData translationInputData) {
        TranslationOutputData outputData = translationDataAccessObject.translate(translationInputData.getTextToTranslate());

        return outputData.getTranslatedText();
    }
}
