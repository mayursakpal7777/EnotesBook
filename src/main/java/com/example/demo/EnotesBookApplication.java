package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAware")
public class EnotesBookApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnotesBookApplication.class, args);
	}

}
