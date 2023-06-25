package com.alga.algafood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.alga.algafood.infraestructure.repository.CustomJpaRespositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRespositoryImpl.class)
public class AlgafoodApiAgoravaiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlgafoodApiAgoravaiApplication.class, args);
	}

}
