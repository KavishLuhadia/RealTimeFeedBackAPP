package com.kavish.httpClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class PreJava11RestCall {

	@Value("${API_KEY}")
	private String azureApiKey;
	
	private String azureEndpoint = "https://azguy-feedback.cognitiveservices.azure.com/";
	
	private static final String API_KEY_HEADER_NAME = "Ocp-Apim-Subscription-Key";

	@Test
	void callTextAnalyticsTest(){
		InputStream iStream = null;
		BufferedReader reader = null;
		HttpURLConnection connection = null;
		URL url;

		try{
		 url = new URL(String.format("%s%s", this.azureEndpoint, "/text/analytics/v3.0/entities/recognition/general"));
		//open the connection to url and specify request param
		connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty(API_KEY_HEADER_NAME, azureApiKey);
		connection.setDoOutput(true);
		// Send request body
		connection.getOutputStream().write("{\"documents\":[{\"id\":\"1\",\"text\":\"The Landon Hotel was found in 1952 London by Arthur Landon after World War II.\"}]}}".getBytes());
		connection.getOutputStream().close();
		
		// Open input stream and receive response
		 iStream = connection.getInputStream();
		 reader = new BufferedReader(new InputStreamReader(iStream));
		StringBuilder responseText = new StringBuilder();
		
		String line;
		
		while ((line = reader.readLine()) != null) {
			responseText.append(line);
			responseText.append("\r");
		}
		assertNotNull(responseText.toString());
		System.out.println(responseText.toString());
	}
	catch(IOException ioe){

	}
	finally{
		try{
		reader.close();
		iStream.close();
		connection.disconnect();
		}
		catch(IOException ioe){

		}

	}
		
	}

}
