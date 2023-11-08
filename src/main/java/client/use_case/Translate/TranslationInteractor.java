package client.use_case.Translate;

public class TranslationInteractor implements TranslationInputBoundary{
    private final TranslationOutputBoundary translationPresenter;
    private final TranslateDataAccessInterface translationDataAccessObject;

    public TranslationInteractor(TranslateDataAccessInterface translationDataAccessObject, TranslationOutputBoundary translationPresenter) {
        this.translationDataAccessObject = translationDataAccessObject;
        this.translationPresenter = translationPresenter;
    }

    @Override
    public void execute(TranslationInputData translationInputData) {
        TranslationOutputData outputData = translationDataAccessObject.translate(translationInputData.getTextToTranslate());

        translationPresenter.presentTranslationResult(outputData);
    }
}
