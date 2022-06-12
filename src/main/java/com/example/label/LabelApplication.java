package com.example.label;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@EnableJdbcRepositories(basePackages = {"com.example.label.repository"})
public class LabelApplication {

	public static void main(String[] args) {
		SpringApplication.run(LabelApplication.class, args);
	}

}
