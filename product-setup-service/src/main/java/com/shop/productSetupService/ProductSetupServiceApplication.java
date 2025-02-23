package com.shop.productSetupService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.shop.productSetupService", "com.shop.dto"})
@OpenAPIDefinition(
		info = @Info(title = "My API", version = "1.0", description = "API Documentation")
)
@EnableJpaRepositories("com.shop.productSetupService.repository") // Adjust package name
@EnableDiscoveryClient
public class ProductSetupServiceApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(ProductSetupServiceApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

	}
}
