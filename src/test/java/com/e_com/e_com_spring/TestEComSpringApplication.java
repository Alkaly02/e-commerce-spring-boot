package com.e_com.e_com_spring;

import org.springframework.boot.SpringApplication;

public class TestEComSpringApplication {

	public static void main(String[] args) {
		SpringApplication.from(EComSpringApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
