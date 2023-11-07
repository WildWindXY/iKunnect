package client.data_access.translate;

import client.interface_adapter.TranslationAPI;
import client.use_case.Translate.TranslateDataAccessInterface;
import client.entity.TranslationRequest;
import client.entity.TranslationResponse;
import client.interface_adapter.DeeplAPI;
import client.use_case.Translate.TranslationOutputData;

public class TranslateDataAccess implements TranslateDataAccessInterface {
    private final TranslationAPI deeplAPI;
    public TranslateDataAccess() {
        this.deeplAPI = new DeeplAPI("EN"); //TODO: initialize with different language
    }
    @Override
    public TranslationOutputData translate(String text) {
        TranslationRequest request = new TranslationRequest(text);
        TranslationResponse response = deeplAPI.translate(request);
        return new TranslationOutputData(response.getTranslatedText(), response.getDetectedLanguage());
    }
}
