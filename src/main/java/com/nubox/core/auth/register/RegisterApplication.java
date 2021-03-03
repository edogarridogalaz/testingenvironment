package com.nubox.core.auth.register;

import io.fusionauth.client.FusionAuthClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RegisterApplication {

    @Value("${fusionauth.apiKey}")
    String apiKey;

    @Value("${fusionauth.host}")
    String host;

    public static void main(String[] args) {
        SpringApplication.run(RegisterApplication.class, args);
    }


    @Bean
    public FusionAuthClient getFusionAuthClient() {
        return new FusionAuthClient(apiKey, host);
    }
}
