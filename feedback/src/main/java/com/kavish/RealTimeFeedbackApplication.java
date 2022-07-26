package com.kavish;
import com.kavish.model.request.SentimentAnalysis;
import com.kavish.model.request.StreamResponse;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.function.client.WebClient;

import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.form.FormEncoder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


@Profile("!test")
@SpringBootApplication
public class RealTimeFeedbackApplication implements CommandLineRunner {
	
	private final String from = "+18596483266";
    private final String to = "+353899506540";
    private final String url = "https://api.twilio.com";
    private final String accountId = "AC684dcd3c95911f2636dca9d74c66821e";



	@Bean
	public ObjectMapper mapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper;
	}
	

	@Autowired
	private AzureSentimentAnalysisService azureSentimentService;
	

	private final String bearerToken = "<your bearer token>";
       

	private final static String API_TWITTER_ENDPOINT = "https://api.twitter.com";

	private final static String API_TWITTER_STREAM_PATH = "/2/tweets/search/stream";
        

	
        
	@Autowired
	private WebClient.Builder builder;
	

	@Bean
	public TwilioClient twilioClient() {
		
        BasicAuthRequestInterceptor interceptor = new BasicAuthRequestInterceptor(accountId, "d2358b4920bc75af3f32a11576507bf7");
		
		return Feign.builder()
				.requestInterceptor(interceptor)
				.encoder(new FormEncoder())
				.target(TwilioClient.class, url);
					
	}
	
	public static void main(String[] args) {
		SpringApplication.run(RealTimeFeedbackApplication.class, args);
	}

	@Override
	public void run(String... args) {
		 try{
		//1.  Subscribe a lambda function to the TwitterStreamingService's stream method
		WebClient client = this.builder
		.baseUrl(API_TWITTER_ENDPOINT)
		.defaultHeaders(headers -> headers.setBearerAuth(this.bearerToken))
		.build();

 client.get()
		.uri(API_TWITTER_STREAM_PATH)
		.retrieve()
		.bodyToFlux(String.class)
		.filter(tweet -> !tweet.isBlank()).subscribe(tweet -> {

			System.out.println("The tweet says: " + tweet);

			try {

				//2.  Within the lambda, deserialize the json from Twitter into a StreamResponse
				StreamResponse response = this.mapper().readValue(tweet, StreamResponse.class);
				
				
				//3.  Using the AzureSentimentService, send the text to Cognitive Services for Sentiment Analysis
				SentimentAnalysis analysis = this.azureSentimentService
						.requestSentimentAnalysis(response.getData().getText(), "en");

				String message = analysis.getSentiment().equalsIgnoreCase("positive")
						? "You received positive feedback on twitter!"
						: "You received negative feedback on Twitter!";
				
				//4.  Print the result to the console
				System.out.println(message);
				

				this.twilioClient().sendMessage(
						accountId, 
						to, 
						from, 
						message);

			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}

		});
		
	}


catch(Exception e){
	e.printStackTrace();
}
	}
}