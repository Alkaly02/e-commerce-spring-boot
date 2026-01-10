package com.e_com.e_com_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.e_com.e_com_spring.repository")
public class EComSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(EComSpringApplication.class, args);
	}

}
