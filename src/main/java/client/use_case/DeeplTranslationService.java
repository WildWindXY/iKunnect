package client.use_case;

import client.entity.TranslationRequest;
import client.entity.TranslationResponse;
import client.interface_adapter.TranslationAPI;

public class DeeplTranslationService implements TranslationService {

    private final TranslationAPI api;

    public DeeplTranslationService(TranslationAPI api) {
        this.api = api;
    }

    @Override
    public TranslationResponse requestTranslate(TranslationRequest request) {
        return api.translate(request);
    }
}
