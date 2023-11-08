
import client.data_access.translate.TranslateDataAccess;
import client.interface_adapter.Translation.TranslationPresenter;
import client.use_case.Translate.TranslateDataAccessInterface;
import client.use_case.Translate.TranslationInputBoundary;
import client.use_case.Translate.TranslationInputData;
import client.use_case.Translate.TranslationInteractor;

import static org.junit.Assert.*;
public class TranslationTest {
    @org.junit.Test
    public void testTranslation() {
        TranslationPresenter translationPresenter = new TranslationPresenter();
        TranslateDataAccessInterface translationDataAccessObject = new TranslateDataAccess();
        TranslationInputBoundary translationInteractor = new TranslationInteractor(translationDataAccessObject,translationPresenter);

        TranslationInputData data = new TranslationInputData("您好,我是一个学生");
        translationInteractor.execute(data);

    }
}
