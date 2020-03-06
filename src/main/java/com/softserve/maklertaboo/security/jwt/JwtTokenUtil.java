package com.softserve.maklertaboo.security.jwt;

import com.softserve.maklertaboo.entity.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenUtil implements Serializable {

    @Value("${spring.security.token.accessExpirationTime}")
    private String accessExpirationTime;
//    private static final long serialVersionUID = -2550185165626007488L;
//    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    @Value("${spring.security.token.secret}")
    private String secret;

    public String createAccessToken (User user) {
        Claims claims = Jwts.claims();
        claims.put("id", user.getId());
        claims.put("email", user.getEmail());
        claims.put("role", user.getRole().name());
        claims.put("username", user.getUsername());
        Date expiryDate = new Date(new Date().getTime() + Long.valueOf(accessExpirationTime));
        log.info("Access Token for " + user.getEmail() + " created.");
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
//
//    private Boolean isTokenExpired(String token) {
//        final Date expiration = getExpirationDateFromToken(token);
//        return expiration.before(new Date());
//    }
//
//    public Boolean validateToken(String token, UserDetailsImpl userDetails) {
//        final String username = getUsernameFromToken(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }
}
