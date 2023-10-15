package main.java;//import org.apache.logging.log4j.LogManager;
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
//
//    public class IKunnect {
//
//       // private static final Logger logger = LogManager.getLogger(IKunnect.class);
//        public static void main(String[] args) {
//
//            try {
//                String text = "你好世界";
//                String sourceLang = "ZH";
//                String targetLang = "EN-US";
//                String encodedText = URLEncoder.encode(text, Charset.forName("UTF-8").toString());
//                String requestBody = String.format("text=%s&source_lang=%s&target_lang=%s", encodedText, sourceLang, targetLang);
//
//                HttpURLConnection connection = getHttpURLConnection(requestBody);
//
//                int responseCode = connection.getResponseCode();
//                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                String inputLine;
//                StringBuilder response = new StringBuilder();
//
//                while ((inputLine = in.readLine()) != null) {
//                    response.append(inputLine);
//                }
//                System.out.println("Response Code: " + responseCode);
//                System.out.println("Response Data: " + response);
//                connection.disconnect();
//            } catch (Exception e) {
//                System.out.println("An error occurred in the main method");
//               // logger.error("An error occurred in the main method", e);
//            }
//        }
//
//        private static HttpURLConnection getHttpURLConnection(String requestBody) throws IOException {
//            URL url = new URL("https://api-free.deepl.com/v2/translate");
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("POST");
//            connection.setRequestProperty("Authorization", "DeepL-Auth-Key ce7a4edb-7949-68cb-4f80-49104d53fe70:fx");
//            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            connection.setDoOutput(true);
//
//            // Specify the UTF-8 character encoding for the request body
//            try (OutputStream os = connection.getOutputStream()) {
//                byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
//                os.write(input);
//            }
//
//            return connection;
//        }
//    }


public class IKunnect {

    public static void main(String[] args) {
        TranslationAPI deeplAPI = new DeeplAPI();
        TranslationService service = new DeeplTranslationService(deeplAPI);

        TranslationRequest request = new TranslationRequest("ZH", "EN", "您好");
        TranslationResponse response = service.requestTranslate(request);

        System.out.println("Translated Text: " + response.getTranslatedText());
    }
}
