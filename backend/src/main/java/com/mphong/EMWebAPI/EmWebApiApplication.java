package com.mphong.EMWebAPI;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Objects;

@SpringBootApplication
public class EmWebApiApplication {

	public static void main(String[] args) {
		// LOAD ENVIRONMENT
		Dotenv env = Dotenv.load();
		System.setProperty("MYSQL_HOST", Objects.requireNonNull(env.get("MYSQL_HOST")));
		System.setProperty("MYSQL_PORT", Objects.requireNonNull(env.get("MYSQL_PORT")));
		System.setProperty("MYSQL_DB", Objects.requireNonNull(env.get("MYSQL_DB")));
		System.setProperty("MYSQL_USER", Objects.requireNonNull(env.get("MYSQL_USER")));
		System.setProperty("MYSQL_PASSWORD", Objects.requireNonNull(env.get("MYSQL_PASSWORD")));

		SpringApplication.run(EmWebApiApplication.class, args);
	}

}
