package com.acgnu.origin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

//@MapperScan("com.acgnu.origin.mapper")
//@ComponentScan("com.acgnu.origin")
@SpringBootApplication
@EnableScheduling
public class OriginApplication {
    public static void main(String[] args) {
        SpringApplication.run(OriginApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }
}
