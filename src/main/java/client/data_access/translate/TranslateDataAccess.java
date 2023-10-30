package client.data_access.translate;

import client.data_access.TranslateDataAccessInterface;
import client.entity.TranslationRequest;
import client.entity.TranslationResponse;
import client.interface_adapter.DeeplAPI;
import client.interface_adapter.TranslationAPI;
import client.use_case.DeeplTranslationService;
import client.use_case.TranslationService;

import java.nio.charset.Charset;

public class TranslateDataAccess implements TranslateDataAccessInterface {
    private final TranslationService translationService;
    public TranslateDataAccess() {
        this.translationService = new DeeplTranslationService( new DeeplAPI("EN")); //TODO: initialize with different language
    }
    @Override
    public String translate(String text) {
        TranslationRequest request = new TranslationRequest(text);
        TranslationResponse response = translationService.requestTranslate(request);
        return response.getTranslatedText();
    }
}
