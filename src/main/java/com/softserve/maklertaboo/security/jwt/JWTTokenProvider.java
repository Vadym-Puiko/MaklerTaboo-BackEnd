package com.softserve.maklertaboo.security.jwt;

import com.softserve.maklertaboo.entity.user.User;
import com.softserve.maklertaboo.repository.user.UserRepository;
import com.softserve.maklertaboo.security.entity.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultJwtParser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Slf4j
@Component
public class JWTTokenProvider implements Serializable {

    private final UserRepository userRepository;

    @Autowired
    public JWTTokenProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Value("${spring.security.token.accessExpirationTime}")
    private String accessExpirationTime;

    @Value("${spring.security.token.refreshExpirationTime}")
    private String refreshExpirationTime;

    @Value("${spring.security.token.secret}")
    private String secret;

    public String generateAccessToken(Authentication authentication) {
        return this.generateAccessToken(((UserDetailsImpl) authentication.getPrincipal()));
    }

    private String generateAccessToken(UserDetailsImpl userDetails) {
        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
        Date expiryDate = new Date(new Date().getTime() + Long.valueOf(accessExpirationTime));
        log.info("Access Token for " + userDetails.getUsername() + " created.");
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String generateRefreshToken(Authentication authentication) {
        return this.generateRefreshToken(((UserDetailsImpl) authentication.getPrincipal()));
    }

    private String generateRefreshToken(UserDetailsImpl userDetails) {
        User user = userRepository.findUserByEmail(userDetails.getUsername());
        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
        Date expiryDate = new Date(new Date().getTime() + Long.valueOf(refreshExpirationTime));
        log.info("Refresh Token for " + userDetails.getUsername() + " created.");
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, user.getRefreshKey())
                .compact();
    }

    public String getEmailFromJWT(String token) {
        String[] splitToken = token.split("\\.");
        String unsignedToken = splitToken[0] + "." + splitToken[1] + ".";
        DefaultJwtParser parser = new DefaultJwtParser();
        Jwt<?, ?> jwt = parser.parse(unsignedToken);
        log.info("Email " + ((Claims) jwt.getBody()).getSubject() + " from token: " + token);
        return ((Claims) jwt.getBody()).getSubject();
    }

    public boolean isTokenValid(String token, String secretKey) {
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !(claims.getBody().getExpiration().before(new Date()));
        }catch(IllegalArgumentException e){
            log.error("Given token is not valid: " + e.getMessage());
        }
        return false;
    }

}
