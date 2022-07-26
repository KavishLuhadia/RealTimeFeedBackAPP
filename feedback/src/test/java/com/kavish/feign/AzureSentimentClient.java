package com.kavish.feign;

import com.kavish.model.request.KeyPhraseAnalyticsRequest;
import com.kavish.model.response.KeyPhraseAnalyticsResponse;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface AzureSentimentClient {

    @RequestLine("POST /text/analytics/v3.1/keyPhrases")
    @Headers({"Content-Type:application/json","Ocp-Apim-Subscription-Key: {apiKey}"})
    public KeyPhraseAnalyticsResponse analysis(@Param String apiKey, KeyPhraseAnalyticsRequest requestBody);

    
}
