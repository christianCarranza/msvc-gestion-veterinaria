package org.carranza.java.msvc.veterinaria.ms.admin.api.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MsAdminApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsAdminApiGatewayApplication.class, args);
	}

}
