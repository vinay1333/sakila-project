package com.example.sakilla;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class SakillaRestApiApplication {
	public static void main(String[] args) {
		// Load the .env file
		Dotenv dotenv = Dotenv.configure().load();

		// Access the environment variables
		String dbHost = dotenv.get("DB_HOST");
		String dbPort = dotenv.get("DB_PORT");
		String dbName = dotenv.get("DB_NAME");
		String dbUser = dotenv.get("DB_USER");
		String dbPassword = dotenv.get("DB_PASSWORD");

		// Proceed to run the Spring Boot app
		SpringApplication.run(SakillaRestApiApplication.class, args);
	}
}

