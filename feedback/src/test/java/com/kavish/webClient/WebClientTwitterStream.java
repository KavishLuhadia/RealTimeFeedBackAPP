package com.kavish.webClient;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest
public class WebClientTwitterStream {

    private final String TWITTER_API_ENDPOINT = "https://api.twitter.com";
  //  private final String TWITTER_API_STREAM_RULE_PATH = "/2/tweets/search/stream/rules";
    private final String TWITTER_API_STREAM_PATH = "/2/tweets/search/stream";

    private final String bearerToken = "AAAAAAAAAAAAAAAAAAAAAEh6ZAEAAAAAXFb4V5b2RXrNJIB1XhWKXIRsyUw%3DN8NMJZn3uTFYxLfReqGDEzuwiDWC8VHv5jsEHEGxziUvhazxbP";
        
    @Autowired
    private WebClient.Builder webClientBuilder;
     
        @Test
        public void testWeb(){

        WebClient webClient =  webClientBuilder.baseUrl(TWITTER_API_ENDPOINT)
        .defaultHeaders(headers->headers.setBearerAuth(bearerToken))
        .defaultHeaders(headers->headers.setContentType(MediaType.APPLICATION_JSON))
        .build();

        // TwitterStreamRuleRequest requestBody = new TwitterStreamRuleRequest();
        // requestBody.addRule("Linkedin1","Linkedin Tag1");
        

        webClient.get()
        .uri(TWITTER_API_STREAM_PATH)
        .retrieve()
        .bodyToFlux(String.class).subscribe(response -> System.out.println(response));
        // ResponseEntity<String> mono = webClient.post().uri(TWITTER_API_STREAM_RULE_PATH).bodyValue(requestBody).retrieve().toEntity(String.class).block();
        
        // System.out.println(mono.getStatusCode());
        // System.out.println(mono.getBody());
        
        // webClient
        // .post()
        // .uri(TWITTER_API_STREAM_RULE_PATH)
        // .bodyValue(requestBody)
        // .retrieve()
        // .toBodilessEntity()
        // .subscribe(response -> {     
        //     webClient
        //     .get()
        //     .uri(TWITTER_API_STREAM_PATH)
        //     .retrieve()
        //     .bodyToFlux(String.class)
        //     .subscribe(responseJson -> {
        //         System.out.println(responseJson);
        //     });

        // });
       
        try {
            System.in.read();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    
}
}