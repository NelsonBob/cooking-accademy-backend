package com.esgi.pa.bootstrap.configurations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = "com.esgi.pa.server.adapter")
@EnableJpaRepositories("com.esgi.pa.server.repositories")
public class PersistenceConfig {
}
