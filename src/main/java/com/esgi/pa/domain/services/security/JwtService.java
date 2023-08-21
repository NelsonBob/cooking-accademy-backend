package com.esgi.pa.domain.services.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Service définit les méthode de gestion des JWT
 */
@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    /**
     * Extrait l'email utilisateur d'un JWT
     *
     * @param token JWT utilisateur
     * @return email utilisateur
     */
    public String extractUserEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrait les arguments du JWT
     *
     * @param token          JWT utilisateur
     * @param claimsResolver callback fonction qui permet la résolution des claims
     * @param <T>            Type générique representant les claims
     * @return Les claims extraites
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
    }

    /**
     * Méthode permettant la création d'un token
     *
     * @param extraClaims élément supplémentaires à ajouter au token
     * @param userDetails les informations utilisateur
     * @param expiration  temps de validité du token
     * @return un JWT sous forme de String
     */
    public String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
        return Jwts.builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    /**
     * Méthode permettant de générer un token
     *
     * @param extraClaims élément supplémentaires à ajouter au token
     * @param userDetails les informations utilisateur
     * @return un JWT sous forme de String
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    /**
     * Méthode permettant de rafraichir un token
     *
     * @param userDetails les informations utilisateur
     * @return un JWT sous forme de String
     */
    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails, refreshExpiration);
    }

    /**
     * Valide un token
     *
     * @param token       le JWT à valider
     * @param userDetails les informations utilisateur
     * @return true si les info utilisateur correspondent à celle contenu dans le token; else false
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        return extractUserEmail(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Vérifie si le token est toujours valide
     *
     * @param token le JWT
     * @return true si le token est toujours valide; else false
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Permet d'extraire la date d'expiration d'un token
     *
     * @param token
     * @return
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extrait l'ensemble des claims d'un token
     *
     * @param token
     * @return
     */
    private Claims extractAllClaims(String token) {
        return Jwts
            .parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    /**
     * Retourne la clé de signature du token
     *
     * @return clé de signature
     */
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }
}
