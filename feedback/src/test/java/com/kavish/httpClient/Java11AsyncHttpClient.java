package com.kavish.httpClient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.HttpResponse.BodySubscribers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Java11AsyncHttpClient {
    

	@Value("${API_KEY}")
	private String azureApiKey;

	private static final String AZURE_ENDPOINT = "https://azguy-feedback.cognitiveservices.azure.com/";
	
	private static final String AZURE_ENDPOINT_PATH = "/text/analytics/v3.0/entities/recognition/general";

	private static final String API_KEY_HEADER_NAME = "Ocp-Apim-Subscription-Key";

	private static final String CONTENT_TYPE = "Content-Type";

	private static final String APPLICATION_JSON = "application/json";
	
	private static final String EXAMPLE_JSON  = "{"
			+ "  \"documents\": ["
			+ "    {"
			+ "      \"language\": \"en\","
			+ "      \"id\": \"1\","
			+ "      \"text\": \"The Landon Hotel was found in 1952 London by Arthur Landon after World War II.\""
			+ "    }"
			+ "  ]"
			+ "}";

    @Autowired
    public ObjectMapper objMapper;

	@Test
	public void getEntities() throws IOException, InterruptedException {
		
		// 1.  Create a client 

        HttpClient httpClient = HttpClient.newHttpClient();

       
                                    
		
		// 2.  Create the request
		
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(AZURE_ENDPOINT+ AZURE_ENDPOINT_PATH))
        .header(API_KEY_HEADER_NAME, azureApiKey)
        .header(CONTENT_TYPE, APPLICATION_JSON)
        .POST(BodyPublishers.ofString(EXAMPLE_JSON))
        .build();

		// 3.  Send the request and receive response

        httpClient.sendAsync(request, BodyHandlers.ofString())
        .thenApply(response -> response.body())
        .thenAccept( body -> {
            try {
                JsonNode jsonNode = objMapper.readValue(body,JsonNode.class);
                String keyPhrase = jsonNode.get("documents").get(0).get("keyPhrases").get(0).asText();
                System.out.println(keyPhrase);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }});

	
        }
}


