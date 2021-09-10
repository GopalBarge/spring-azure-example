package com.sal.prompt.web.client.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfig {



    @Value("${client.rest.user.name}")
    private String userName;

    @Value("${client.rest.user.password}")
    private String userPassword;
    @Bean
    RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.basicAuthentication(userName, userPassword).build();
    }
}
