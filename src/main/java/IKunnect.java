//import org.apache.logging.log4j.Logger;

import client.data_access.TranslateDataAccessInterface;
import client.data_access.translate.TranslateDataAccess;
import client.entity.TranslationRequest;
import client.entity.TranslationResponse;
import client.interface_adapter.DeeplAPI;
import client.interface_adapter.TranslationAPI;
import client.use_case.DeeplTranslationService;
import client.use_case.TranslationService;

import java.nio.charset.Charset;

public class IKunnect {
    public static void main(String[] args) {
        // print default text encode mode
        TranslateDataAccessInterface translateDataAccess = new TranslateDataAccess();
        String text = "你说的对,但干净架构是一个由罗伯特老毕登写出来的令人生恶的八股规范,我不想用它";
        System.out.println(translateDataAccess.translate(text));
    }
}
