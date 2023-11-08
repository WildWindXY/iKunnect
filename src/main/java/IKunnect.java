//import org.apache.logging.log4j.Logger;

import client.use_case.Translate.*;
import client.data_access.translate.TranslateDataAccess;

public class IKunnect implements TranslationOutputBoundary {
    public static void main(String[] args) {
        // print default text encode mode
        TranslateDataAccessInterface translateDataAccess = new TranslateDataAccess();
        String text = "你说的对,但干净架构是一个由罗伯特老毕登写出来的令人生恶的八股规范,我不想用它";
        System.out.println(translateDataAccess.translate(text));
    }

    @Override
    public void presentTranslationResult(TranslationOutputData outputData) {
        System.out.println(outputData.getDetectedLanguage()+outputData.getTranslatedText());
    }

}
