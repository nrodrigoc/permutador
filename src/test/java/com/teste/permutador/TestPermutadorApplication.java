package com.teste.permutador;

import org.springframework.boot.SpringApplication;

public class TestPermutadorApplication {

	public static void main(String[] args) {
		SpringApplication.from(PermutadorApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
