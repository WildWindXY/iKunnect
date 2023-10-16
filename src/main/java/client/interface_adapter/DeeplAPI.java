package client.interface_adapter;

import client.entity.TranslationRequest;
import client.entity.TranslationResponse;

import javax.print.DocFlavor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class DeeplAPI implements TranslationAPI {

    private static final String API_URL = "https://api-free.deepl.com/v2/translate";
    private static final String AUTH_KEY = "DeepL-Auth-Key ce7a4edb-7949-68cb-4f80-49104d53fe70:fx";

    @Override
    public TranslationResponse translate(TranslationRequest request) {
        try {
            // original text
            System.out.println(request.getText());
            String encodedText =  URLEncoder.encode(request.getText(), "UTF-8");
            String requestBody = String.format("text=%s&source_lang=%s&target_lang=%s",
                    encodedText, request.getSourceLang(), request.getTargetLang());
            // encoded request
            System.out.println("Request Body:" + requestBody);
            HttpURLConnection connection = getHttpURLConnection(requestBody);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                // original response
                System.out.println(response.toString());
                // Assuming the API returns JSON and we'd parse it to get the translated text
                String translatedText = parseTranslatedText(response.toString());

                connection.disconnect();
                return new TranslationResponse(translatedText);
            } else {
                // Handle non-200 responses if necessary
            }

        } catch (Exception e) {
            System.out.println("An error occurred in the translation process");
            // Handle exceptions
        }
        return new TranslationResponse("");
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

    private String parseTranslatedText(String jsonResponse) {
        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONArray translationsArray = jsonObject.getJSONArray("translations");
        JSONObject translationObject = translationsArray.getJSONObject(0);
        return translationObject.getString("text");
    }

}
