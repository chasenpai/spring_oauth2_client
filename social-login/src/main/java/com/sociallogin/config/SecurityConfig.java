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
        http.authorizeHttpRequests(authorizeHttpRequests -> {
            authorizeHttpRequests.requestMatchers("/social-login").permitAll();
            authorizeHttpRequests.anyRequest().authenticated();
        });
        //oauth2 로그인 설정
        http.oauth2Login(oauth2 -> {
                    oauth2.loginPage("/social-login")
                            .defaultSuccessUrl("/social-user")
                            //사용자 정보 엔드포인트 구성
                            .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint.userService(socialUserService));
        });
        return http.build();
    }

}
