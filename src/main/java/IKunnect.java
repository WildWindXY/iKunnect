
//import org.apache.logging.log4j.Logger;

import client.entity.TranslationRequest;
import client.entity.TranslationResponse;
import client.interface_adapter.DeeplAPI;
import client.interface_adapter.TranslationAPI;
import client.use_case.DeeplTranslationService;
import client.use_case.TranslationService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class IKunnect {
    public static void main(String[] args) {
        TranslationAPI deeplAPI = new DeeplAPI();
        TranslationService service = new DeeplTranslationService(deeplAPI);

        TranslationRequest request = new TranslationRequest("ZH", "EN", "我真的很讨厌干净架构!!！!!");
        TranslationResponse response = service.requestTranslate(request);

        System.out.println("Translated Text: " + response.getTranslatedText());
    }
}
