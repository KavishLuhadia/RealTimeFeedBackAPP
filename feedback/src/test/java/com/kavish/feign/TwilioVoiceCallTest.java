package com.kavish.feign;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.form.FormEncoder;

@SpringBootTest
public class TwilioVoiceCallTest {

    private final String from = "+18596483266";
    private final String to = "+353899786667";
    private final String url = "https://api.twilio.com";
    private final String accountId = "AC684dcd3c95911f2636dca9d74c66821e";

    @Test
    public void makeCall(){

        BasicAuthRequestInterceptor interceptor = new BasicAuthRequestInterceptor(accountId, "d2358b4920bc75af3f32a11576507bf7");
        
        TwilioClient client = Feign.builder()
        .requestInterceptor(interceptor)
        .encoder(new FormEncoder())
        .target(TwilioClient.class, url);
        
        client.sendVoiceMessage(accountId, to, from, "<Response><Say>baby shark dug dug dug</Say></Response>");

    }
    
}
