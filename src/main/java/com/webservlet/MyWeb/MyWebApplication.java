package com.webservlet.MyWeb;

import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyWebApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(MyWebApplication.class);

		// O Render injeta a variavel PORT e so roteia trafego para a porta que
		// ela aponta. Como o Spring Boot mapeia SERVER_PORT -> server.port, mas
		// nao mapeia PORT, sem esta traducao o app escuta na 8080, o Render
		// espera outra porta e o servico responde 502/503 mesmo com tudo certo.
		//
		// Vai como defaultProperties (menor precedencia), entao --server.port,
		// SERVER_PORT e application.properties ainda vencem se estiverem definidos.
		String port = System.getenv("PORT");
		if (port != null && !port.isBlank()) {
			app.setDefaultProperties(Map.of("server.port", port));
		}

		app.run(args);
	}

}
