package com.softserve.maklertaboo.security.filter;

import com.softserve.maklertaboo.constant.ErrorMessage;
import com.softserve.maklertaboo.exception.exceptions.CustomExpiredJwtException;
import com.softserve.maklertaboo.security.jwt.JWTTokenProvider;
import com.softserve.maklertaboo.security.service.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private JWTTokenProvider jwtTokenProvider;
    private AuthenticationManager authenticationManager;
    private UserDetailsServiceImpl userDetailsService;

    @Value("${spring.security.token.secret}")
    private String secret;

    @Autowired
    public JWTAuthenticationFilter(JWTTokenProvider jwtTokenProvider, @Lazy AuthenticationManager authenticationManager,
                                   UserDetailsServiceImpl userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            String accessToken = httpServletRequest.getHeader("Authorization");
            if (StringUtils.hasText(accessToken) && jwtTokenProvider.isTokenValid(accessToken, secret)) {
                String email = jwtTokenProvider.getEmailFromJWT(accessToken);
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (ExpiredJwtException e) {
            final String expiredMsg = e.getMessage();
            logger.warn(expiredMsg);

            final String msg = (expiredMsg != null) ? expiredMsg : "Unauthorized";
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, msg);

//        } catch (Exception e) {
//            log.error("Access denied with token: " + e.getMessage());

        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}

