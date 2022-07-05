package org.carranza.java.msvc.veterinaria.msvcgestionproducto.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonBeans {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
