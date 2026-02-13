package com.shrijal.e_commerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    //for calling external currency API
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


}


