package com.sistemasdistribuidos.restapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RestAPIApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestAPIApplication.class, args);
	}

	@Bean
	public OpenAPI springShopOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("Rest API")
								.description("Rest API para a disciplina de Sistemas Distribuídos")
								.version("v0.0.1")
								.license(new License().name("Apache 2.0").url("https://springdoc.org")));
	}

}
