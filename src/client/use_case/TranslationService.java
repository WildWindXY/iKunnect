package client.use_case;

import client.entity.TranslationRequest;
import client.entity.TranslationResponse;

public interface TranslationService {
    TranslationResponse requestTranslate(TranslationRequest request);
}
