package com.softserve.maklertaboo.security.jwt;

import com.softserve.maklertaboo.entity.user.User;
import com.softserve.maklertaboo.security.entity.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider implements Serializable {

    @Value("${spring.security.token.accessExpirationTime}")
    private String accessExpirationTime;

    @Value("${spring.security.token.secret}")
    private String secret;

    public String generateAccessToken(Authentication authentication) {
        return this.generateAccessToken(((UserDetailsImpl) authentication.getPrincipal()));
    }

    public String generateAccessToken(UserDetailsImpl user) {
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("id", user.getId());
        claims.put("role", user.getAuthorities());
        Date expiryDate = new Date(new Date().getTime() + Long.valueOf(accessExpirationTime));
        log.info("Access Token for " + user.getUsername() + " created.");
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

//    public String getUsernameFromToken(String token) {
//        return getClaimFromToken(token, Claims::getSubject);
//    }
//
//    public Date getExpirationDateFromToken(String token) {
//        return getClaimFromToken(token, Claims::getExpiration);
//    }
//
//    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = getAllClaimsFromToken(token);
//        return claimsResolver.apply(claims);
//    }
//
//    private Claims getAllClaimsFromToken(String token) {
//        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
//    }

    public boolean isTokenValid(String token) {
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return !(claims.getBody().getExpiration().before(new Date()));
        }catch(IllegalArgumentException e){
            log.error("Given token is not valid: " + e.getMessage());
        }
        return false;
    }

}
