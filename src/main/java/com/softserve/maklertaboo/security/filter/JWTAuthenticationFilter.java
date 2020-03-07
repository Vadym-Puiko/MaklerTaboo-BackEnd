package com.softserve.maklertaboo.security.filter;

import com.softserve.maklertaboo.security.jwt.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@NoArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private JwtTokenUtil jwtTokenUtil;
    private AuthenticationManager authenticationManager;

    @Autowired
    public JWTAuthenticationFilter(JwtTokenUtil jwtTokenUtil, AuthenticationManager authenticationManager) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = httpServletRequest.getHeader("token");
        if (accessToken != null) {
            try {
                if (jwtTokenUtil.isTokenValid(accessToken)) {
                    Authentication authentication = authenticationManager
                            .authenticate(new UsernamePasswordAuthenticationToken(accessToken, null));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (ExpiredJwtException e) {
                log.info("Token has expired: " + accessToken);
            } catch (Exception e) {
                log.info("Access denied with token: " + e.getMessage());
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
