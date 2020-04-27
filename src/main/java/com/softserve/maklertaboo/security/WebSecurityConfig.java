package com.softserve.maklertaboo.security;

import com.softserve.maklertaboo.security.filter.JWTAuthenticationFilter;
import com.softserve.maklertaboo.security.jwt.JWTAuthenticationEntryPoint;
import com.softserve.maklertaboo.security.service.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
@AllArgsConstructor
@PropertySource("classpath:/spring-security.properties")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final JWTAuthenticationEntryPoint entryPoint;
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(entryPoint)
                .and()
                .authorizeRequests()
                .antMatchers("/users/create").permitAll()
                .antMatchers("/users/signIn").permitAll()
                .antMatchers("/users/refreshTokens").permitAll()
                .antMatchers("/users/confirmRegistration/*").permitAll()
                .antMatchers("/users/resendRegistrationToken/*").permitAll()
                .antMatchers(HttpMethod.GET, "/users/*").permitAll()
                .antMatchers(HttpMethod.GET, "/flat/*").permitAll()
                .antMatchers("/flat/userflat/*").permitAll()
                .antMatchers("/flat/search/*").permitAll()
                .antMatchers(HttpMethod.GET, "/tag/**").permitAll()
                .antMatchers("/map/**").permitAll()
                .antMatchers("/booking/new-requests").permitAll()
                .antMatchers("/wss/**").permitAll()
                .antMatchers("/flatcomments/getall/*").permitAll()
                .antMatchers("/flatcomments/getallbylikes/*").permitAll()
                .antMatchers("/flatcomments/getallaboutcomment/*").permitAll()
                .antMatchers("/usercomments/getall/*").permitAll()
                .antMatchers("/usercomments/getallbylikes/*").permitAll()
                .antMatchers("/usercomments/getallaboutcomment/*").permitAll()
                .anyRequest().authenticated();

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**"
        );
    }


}
