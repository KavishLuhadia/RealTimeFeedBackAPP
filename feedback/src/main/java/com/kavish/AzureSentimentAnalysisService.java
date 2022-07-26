package com.kavish;
import java.io.IOException;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kavish.model.request.SentimentAnalysis;
import com.kavish.model.request.TextAnalyticsRequest;
import com.kavish.model.request.TextDocument;

@Service
public class AzureSentimentAnalysisService {

	private String azureApiKey = "<your api key>";
		
	@Autowired
	private ObjectMapper mapper;
	
	private static final String AZURE_ENDPOINT = "https://azguy-feedback.cognitiveservices.azure.com/";

	private static final String API_KEY_HEADER_NAME = "Ocp-Apim-Subscription-Key";

	private static final String CONTENT_TYPE = "Content-Type";

	private static final String APPLICATION_JSON = "application/json";

	public SentimentAnalysis requestSentimentAnalysis(String text, String language) throws IOException, InterruptedException {

		TextDocument document = new TextDocument("1",text,language);
		TextAnalyticsRequest requestBody = new TextAnalyticsRequest();
		requestBody.getDocuments().add(document);
		
		String endpoint = AZURE_ENDPOINT + "/text/analytics/v3.1/sentiment";

        // Build Client
		HttpClient client = HttpClient.newBuilder()
				.version(Version.HTTP_2)
				.proxy(ProxySelector.getDefault())
				.connectTimeout(Duration.ofSeconds(5))
				.build();
		
        // Build Request
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(endpoint))
				.header(API_KEY_HEADER_NAME, this.azureApiKey)
				.header(CONTENT_TYPE, APPLICATION_JSON)
				.POST(BodyPublishers.ofString(mapper.writeValueAsString(requestBody)))
				.timeout(Duration.ofSeconds(5))
				.build();
		
        // Execute Request        
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		
		if(response.statusCode() != 200) {
			System.out.println(response.body());
			throw new RuntimeException("An issue occurred making the API call");
		}
	
		String sentimentValue = this.mapper
				.readValue(response.body(), JsonNode.class)
				.get("documents")
				.get(0)
				.get("sentiment")
				.asText();

		SentimentAnalysis analysis = new SentimentAnalysis(document, sentimentValue);
		
		return analysis;

	}
}
