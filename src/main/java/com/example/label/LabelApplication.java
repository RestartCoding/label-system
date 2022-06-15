package com.example.label;

import com.example.label.util.SpringBeanUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

/**
 * @author jack
 */
@SpringBootApplication
@EnableJdbcRepositories(basePackages = {"com.example.label.repository"})
public class LabelApplication {

	public static void main(String[] args) {
		SpringBeanUtils.ac = SpringApplication.run(LabelApplication.class, args);
	}

}
