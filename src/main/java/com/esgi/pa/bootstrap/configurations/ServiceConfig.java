package com.esgi.pa.bootstrap.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.esgi.pa.server.repositories.UsersRepository;

@Configuration
@RequiredArgsConstructor
@ComponentScan(basePackages = "com.esgi.pa.domain.services")
public class ServiceConfig {

    private final UsersRepository usersRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return (String username) -> usersRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
