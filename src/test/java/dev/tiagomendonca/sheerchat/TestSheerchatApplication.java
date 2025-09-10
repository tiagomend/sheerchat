package dev.tiagomendonca.sheerchat;

import org.springframework.boot.SpringApplication;

public class TestSheerchatApplication {

	public static void main(String[] args) {
		SpringApplication.from(SheerchatApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
