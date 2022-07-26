package com.kavish;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface TwilioClient {
   
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @RequestLine("POST /2010-04-01/Accounts/{AccountSID}/Calls.json")
    public void sendVoiceMessage(@Param("AccountSID") String accountId,
    @Param("To") String to,
    @Param("From") String from,
    @Param("Twiml") String twiml);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @RequestLine("POST /2010-04-01/Accounts/{AccountSID}/Messages.json")
    public void sendMessage(@Param("AccountSID") String accountId,
    @Param("To") String to,
    @Param("From") String from,
    @Param("Body") String body);
}
