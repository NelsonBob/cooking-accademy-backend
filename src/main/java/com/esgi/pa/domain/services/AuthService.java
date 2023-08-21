package com.esgi.pa.domain.services;

import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.enums.RoleEnum;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.security.JwtService;
import com.esgi.pa.server.repositories.UsersRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Serivce d'authentification de l'application
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Permet la création d'un nouvel utilisateur
     *
     * @param name     nom de l'utilisateur
     * @param email    email de l'utilisateur
     * @param password mot de passe de l'utilisateur
     * @param role     rôle de l'utilisateur
     * @return un token JWT pour l'utilisateur
     * @throws TechnicalFoundException si l'utilisateur existe déjà
     */
    public String create(String name, String email, String password, RoleEnum role) throws TechnicalFoundException {
        if (usersRepository.findByEmail(email).isEmpty()) {
            User savedUser = usersRepository.save(
                User.builder()
                    .name(name)
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .role(role)
                    .build());
            User user = usersRepository.findByEmail(savedUser.getEmail()).orElseThrow();
            return jwtService.generateToken(
                Map.of("id", user.getId(),
                    "name", user.getName(),
                    "role", user.getRole()),
                user);
        } else {
            throw new TechnicalFoundException("Un compte existe Déjà avec cet email :" + email);
        }
    }

    /**
     * Permet la connexion d'un utilisateur existant
     *
     * @param email    email de l'utilisateur
     * @param password mot dep passe de l'utilisateur
     * @return un token JWT pour l'utilisateur
     * @throws TechnicalNotFoundException si l'utilisateur n'existe pas
     */
    public String login(String email, String password) throws TechnicalNotFoundException {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                email,
                password));
        User user = usersRepository.findByEmail(email)
            .orElseThrow(() -> new TechnicalNotFoundException(NOT_FOUND, "Username not found with email : " + email));
        return jwtService.generateToken(
            Map.of("id", user.getId(),
                "name", user.getName(),
                "role", user.getRole()),
            user);
    }
}
