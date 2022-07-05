package org.carranza.java.msvc.veterinaria.msvcadminserveradmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

@EnableAdminServer
@EnableDiscoveryClient
@SpringBootApplication
public class MsvcAdminServerAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcAdminServerAdminApplication.class, args);
	}

}
