package org.carranza.java.msvc.veterinaria.msvcadminserverconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class MsvcAdminServerConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcAdminServerConfigApplication.class, args);
	}

}
