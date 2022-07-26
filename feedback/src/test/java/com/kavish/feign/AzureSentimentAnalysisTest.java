package com.kavish.feign;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import com.kavish.model.request.Document;
import com.kavish.model.request.KeyPhraseAnalyticsRequest;
import com.kavish.model.response.KeyPhraseAnalyticsResponse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

@SpringBootTest
public class AzureSentimentAnalysisTest {
    private static final String AZURE_ENDPOINT = "https://azguy-feedback.cognitiveservices.azure.com/";
   
    //@Value("${API_KEY}")
    private String azureApiKey =  "b57ad7825bb0491c9e733f493c8ab6e1";
    
    @Test
    public void getKeyPhrases() throws IOException, InterruptedException,Exception {

        Document document = new Document("en", "1", "Dublin is beautiful");
        KeyPhraseAnalyticsRequest requestBody = new KeyPhraseAnalyticsRequest();
        requestBody.getDocuments().add(document);

     
        AzureSentimentClient client =   Feign.builder()
                                        .decoder(new JacksonDecoder())
                                        .encoder(new JacksonEncoder())
                                        .target(AzureSentimentClient.class, AZURE_ENDPOINT);
                                        

        KeyPhraseAnalyticsResponse response =  client.analysis(azureApiKey, requestBody);
        assertNotNull(response);
        System.out.println(response.getDocuments().get(0).getKeyPhrases());
        



        
}
}