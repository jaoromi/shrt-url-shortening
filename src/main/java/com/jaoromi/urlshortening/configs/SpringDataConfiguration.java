package com.jaoromi.urlshortening.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"com.jaoromi.urlshortening.admin.repositories"})
@EnableMongoRepositories(basePackages = {"com.jaoromi.urlshortening.shrt.repositories"})
public class SpringDataConfiguration {
}
