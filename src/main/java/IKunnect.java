//import org.apache.logging.log4j.Logger;

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
        System.out.println(Charset.defaultCharset());
        TranslationAPI deeplAPI = new DeeplAPI();
        TranslationService service = new DeeplTranslationService(deeplAPI);

        TranslationRequest request = new TranslationRequest("ZH", "EN", "测试DeepL翻译API,中翻英测试" );
        TranslationResponse response = service.requestTranslate(request);


        System.out.println("Translated Text: " + response.getTranslatedText());

        request = new TranslationRequest("EN", "ZH", "Test DeepL translation API, English translate to Chinese" );
        response = service.requestTranslate(request);


        System.out.println("Translated Text: " + response.getTranslatedText());

    }
}
