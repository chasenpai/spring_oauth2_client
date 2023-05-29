package com.sociallogin.config;

import com.sociallogin.service.SocialUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private SocialUserService socialUserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(
                csrf -> csrf.disable()
        );
        http.authorizeHttpRequests(
                authorizeHttpRequests -> authorizeHttpRequests.anyRequest().authenticated()
        );
        http.oauth2Login(
                oauth2 -> oauth2.userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint.userService(socialUserService))
        );
        return http.build();
    }

}
