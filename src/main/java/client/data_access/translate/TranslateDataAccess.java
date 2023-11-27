package client.data_access.translate;

import client.use_case.Translate.TranslateDataAccessInterface;
import client.entity.TranslationRequest;
import client.entity.TranslationResponse;
import client.use_case.Translate.TranslationOutputData;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class TranslateDataAccess implements TranslateDataAccessInterface {
    private final DeeplAPI deeplAPI;
    public TranslateDataAccess() {
        this.deeplAPI = new DeeplAPI("EN"); //TODO: initialize with different language
    }
    @Override
    public TranslationOutputData translate(String text) {
        TranslationRequest request = new TranslationRequest(text);
        TranslationResponse response = deeplAPI.translate(request);
        return new TranslationOutputData(response.getTranslatedText(), response.getDetectedLanguage());
    }

    class DeeplAPI  {

        private static final String API_URL = "https://api-free.deepl.com/v2/translate";
        private static final String AUTH_KEY = "DeepL-Auth-Key ce7a4edb-7949-68cb-4f80-49104d53fe70:fx";

        private final String target_lang;

        public DeeplAPI(String target_lang) {
            this.target_lang = target_lang;
        }

        public TranslationResponse translate(TranslationRequest request) {
            try {
                // original text
                System.out.println(request.getText());
                String encodedText = URLEncoder.encode(request.getText(), "UTF-8");
                String requestBody = String.format("text=%s&target_lang=%s",
                        encodedText, this.target_lang);
                // encoded request
//            System.out.println("Request Body:" + requestBody);
                HttpURLConnection connection = getHttpURLConnection(requestBody);

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    // original response
//                System.out.println(response.toString());
                    // Assuming the API returns JSON and we'd parse it to get the translated text

                    connection.disconnect();
                    return parseTranslatedText(response.toString());
                } else {
                    // Handle non-200 responses if necessary
                }

            } catch (Exception e) {
                System.out.println("An error occurred in the translation process");
                // Handle exceptions
            }
            return null; // TODO: raise exception
        }

        private HttpURLConnection getHttpURLConnection(String requestBody) throws IOException {
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", AUTH_KEY);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", String.valueOf(requestBody.length()));


            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            return connection;
        }

        //    private String parseTranslatedText(String jsonResponse) {
//        JSONObject jsonObject = new JSONObject(jsonResponse);
//        JSONArray translationsArray = jsonObject.getJSONArray("translations");
//        JSONObject translationObject = translationsArray.getJSONObject(0);
//        return translationObject.getString("text");
//    }
        private TranslationResponse parseTranslatedText(String jsonResponse) {
            Gson gson = new Gson();
            DeeplJsonCallBackFormat response = gson.fromJson(jsonResponse, DeeplJsonCallBackFormat.class);
            return new TranslationResponse(response.getTranslations().get(0).getText(), response.getTranslations().get(0).getDetectedSourceLanguage());
        }

        private static class DeeplJsonCallBackFormat {
            private List<DeeplJsonCallBackInstance> translations;

            public List<DeeplJsonCallBackInstance> getTranslations() {
                return translations;
            }
        }

        private static class DeeplJsonCallBackInstance {
            private String detected_source_language;
            private String text;

            public String getDetectedSourceLanguage() {
                return detected_source_language;
            }

            public String getText() {
                return text;
            }
        }

    }
}
