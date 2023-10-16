package client.interface_adapter;

import client.entity.TranslationRequest;
import client.entity.TranslationResponse;

public interface TranslationAPI {
    TranslationResponse translate(TranslationRequest request);
}
