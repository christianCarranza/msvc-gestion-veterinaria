package org.carranza.java.msvc.veterinaria.msvcgestionproducto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MsvcVeterinariaGestionProductoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcVeterinariaGestionProductoApplication.class, args);
	}

}
