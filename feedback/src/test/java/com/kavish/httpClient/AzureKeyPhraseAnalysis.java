package com.kavish.httpClient;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.HttpResponse.BodySubscriber;
import java.net.http.HttpResponse.BodySubscribers;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kavish.model.request.Document;
import com.kavish.model.request.KeyPhraseAnalyticsRequest;
import com.kavish.model.response.KeyPhraseAnalyticsResponse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AzureKeyPhraseAnalysis {

    @Value("${API_KEY}")
    private String azureApiKey;

    private static final String AZURE_ENDPOINT = "https://azguy-feedback.cognitiveservices.azure.com/";

    private static final String AZURE_ENDPOINT_PATH = "/text/analytics/v3.1/keyPhrases";

    private static final String API_KEY_HEADER_NAME = "Ocp-Apim-Subscription-Key";

    private static final String CONTENT_TYPE = "Content-Type";

    private static final String APPLICATION_JSON = "application/json";

    @Autowired
    public ObjectMapper objMapper;
    
    @Test
    public void getKeyPhrases() throws IOException, InterruptedException,Exception {

        Document document = new Document("en", "1", "Dublin is beautiful");
        KeyPhraseAnalyticsRequest requestBody = new KeyPhraseAnalyticsRequest();
        requestBody.getDocuments().add(document);

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(AZURE_ENDPOINT + AZURE_ENDPOINT_PATH))
                .header(API_KEY_HEADER_NAME, azureApiKey)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .POST(BodyPublishers.ofString(objMapper.writeValueAsString(requestBody)))
                .build();

     //   HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
       HttpResponse<String> response = client
       .send(request, responseInfo ->
          BodySubscribers.mapping(BodySubscribers.ofString(StandardCharsets.UTF_8), String::toString));

          System.out.println(response.body());

        HttpResponse<KeyPhraseAnalyticsResponse> response1 = client
          .send(request, new JsonBodyHandler<>(KeyPhraseAnalyticsResponse.class));

       System.out.println(response1.body());

        // For Single object return
        // objMapper.readValue(response.body(), responseType);

        // For List<KeyPhraseAnalyticsResponse>
        // objMapper.readValue(response.body(),
        // objMapper.getTypeFactory().constructCollectionType(List.class,
        // responseType));

        // HttpClient client = HttpClient.newHttpClient();

    }
}

 class JsonBodyHandler<W> implements HttpResponse.BodyHandler<W> {

    private final Class<W> keyPhraseAnalyticsResponse;

    public JsonBodyHandler(Class<W> keyPhraseAnalyticsResponse) {
        this.keyPhraseAnalyticsResponse = keyPhraseAnalyticsResponse;
    }

    @Override
    public HttpResponse.BodySubscriber<W> apply(HttpResponse.ResponseInfo responseInfo) {
        return asJSON(keyPhraseAnalyticsResponse);
    }

    public static <W> HttpResponse.BodySubscriber<W> asJSON(Class<W> targetType) {
        HttpResponse.BodySubscriber<String> upstream = HttpResponse.BodySubscribers.ofString(StandardCharsets.UTF_8);

        return HttpResponse.BodySubscribers.mapping(
                upstream,
                (String body) -> {
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        return objectMapper.readValue(body, targetType);
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
            });
}
 }
