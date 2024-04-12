package com.jpa.user1984;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class User1984Application {

	public static void main(String[] args) {
		SpringApplication.run(User1984Application.class, args);
	}

}
