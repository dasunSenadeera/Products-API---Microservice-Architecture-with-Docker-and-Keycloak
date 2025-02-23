package org.shop.productSetupService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.shop.productSetupService", "com.shop.dto"})
@OpenAPIDefinition(
        info = @Info(title = "My API", version = "1.0", description = "API Documentation")
)
@EnableDiscoveryClient
@EnableRabbit
public class ProductSearchServiceApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(ProductSearchServiceApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
