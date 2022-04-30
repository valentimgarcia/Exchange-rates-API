package com.example.Exchange.Rates.API;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ExchangeRatesApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExchangeRatesApiApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder templateBuilder) {
		return templateBuilder.build();
	}
}
