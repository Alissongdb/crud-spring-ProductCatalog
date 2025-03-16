package com.Alisson.ProductCatalog.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  // Anotação que diz que essa classe contém configurações do Spring
public class ModelMapperConfig {

    @Bean  // Cria um bean do tipo ModelMapper
    public ModelMapper modelMapper() {
        return new ModelMapper();  // Retorna uma nova instância do ModelMapper
    }
}
