package com.kavish.webClient;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

public class WebClientTwitterSearch {
    
    private final String TWITTER_API_ENDPOINT = "https://api.twitter.com";
    private final String TWITTER_API_TWEETS_PATH = "/2/tweets/search/recent";
    private final String bearerToken = "AAAAAAAAAAAAAAAAAAAAAD7jYgEAAAAAucMPjuByZzFA1THfn5eP9c3AL4U%3DaFc4EK9CdGjBoNJmZTjhLQq8Icr3MHhUK129aM6h9BMizs6JKJ";

     
        @Test
        public void testWeb(){
        WebClient webClient = WebClient.create(TWITTER_API_ENDPOINT);

        Mono<String> monoString = webClient.get()
        .uri(TWITTER_API_TWEETS_PATH+ "?query="+ "Linkedin")
        .header("Authorization", "Bearer " +bearerToken)
        .retrieve()
        .bodyToMono(String.class);
        
        String response = monoString.block();

        System.out.println("********************"+response);

        Mono<ResponseEntity<String>> responseEntity = webClient.get()
        .uri(TWITTER_API_TWEETS_PATH+ "?query="+ "Linkedin")
        .header("Authorization", "Bearer " +bearerToken)
        .retrieve()
        .toEntity(String.class);

       

        ResponseEntity<String>responseFromEntity = responseEntity.block();
        System.out.println(responseFromEntity.getStatusCode() + responseFromEntity.getBody());
    

     
    }
}
