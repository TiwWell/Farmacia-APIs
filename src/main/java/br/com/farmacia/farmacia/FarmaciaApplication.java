package br.com.farmacia.farmacia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class FarmaciaApplication {

	public static void main(String[] args) {
		SpringApplication.run(FarmaciaApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/lista-cliente").allowedOrigins("http://localhost:3000");
				registry.addMapping("/atualizar-cliente").allowedOrigins("http://localhost:3000");
				registry.addMapping("/adicionar-cliente").allowedOrigins("http://localhost:3000");
				registry.addMapping("/desativar-cliente").allowedOrigins("http://localhost:3000");
			}
		};
	}

}
