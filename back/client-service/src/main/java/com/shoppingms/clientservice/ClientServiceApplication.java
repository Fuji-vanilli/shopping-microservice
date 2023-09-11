package com.shoppingms.clientservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
@RefreshScope
public class ClientServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientServiceApplication.class, args);
	}

}
