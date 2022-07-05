package org.carranza.java.msvc.veterinaria.msvcadminserverregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class MsvcAdminServerRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcAdminServerRegistryApplication.class, args);
	}

}
