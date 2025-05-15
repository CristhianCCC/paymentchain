package com.paymentchain.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class CustomerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerApplication.class, args);
	}

	//@Bean es para que spring lo catalogue como un metodo reutilizable y pueda ser inyectado en el controller o service
	@Bean
	//LoadBalanced para que esta clase tenga capacidades de balanceo de carga
	@LoadBalanced
	public WebClient.Builder loadBalancedWebClientBuilder(){
		return WebClient.builder();
	}

}
