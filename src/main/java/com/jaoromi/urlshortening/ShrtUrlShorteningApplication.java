package com.jaoromi.urlshortening;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@EnableCaching
public class ShrtUrlShorteningApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShrtUrlShorteningApplication.class, args);
	}
}
