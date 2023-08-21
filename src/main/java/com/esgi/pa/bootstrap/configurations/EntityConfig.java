package com.esgi.pa.bootstrap.configurations;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = "com.esgi.pa.domain.entities")
public class EntityConfig {
}
