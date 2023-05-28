package com.oauth2example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests ->
            authorizeRequests.anyRequest().authenticated()
        )
        //ClientRegistrationRepository 를 Customizer 로 설정 하거나 빈으로 등록해서 사용
        .oauth2Login(oauth2 -> oauth2.clientRegistrationRepository(clientRegistrationRepository()));

        return http.build();
    }

    /**
     * ClientRegistration 을 시큐리티에 등록
     * - ClientRegistrationRepository 객체는 registration id 로 ClientRegistration 을 찾을 수 있다
     */
    public ClientRegistrationRepository clientRegistrationRepository(){
        return new InMemoryClientRegistrationRepository(clientRegistration());
    }

    /**
     * ClientRegistration 정의
     */
    private ClientRegistration clientRegistration(){

//        ClientRegistration cr = ClientRegistration
//                .withRegistrationId("github") //등록 ID 설정
//                .clientId("") //Github OAuth 애플리케이션의 클라이언트 ID 설정
//                .clientSecret("") //Github OAuth 애플리케이션의 클라이언트 시크릿 설정
//                .scope(new String[]{"read:user"}) //요청할 권한 범위 설정
//                .authorizationUri("https://github.com/login/oauth/authorize") //Github OAuth 인증 엔드포인트 URI 설정
//                .tokenUri("https://api.github.com/user") //엑세스 토큰 검색을 위한 Github OAuth 토큰 엔드포인트 URI 설정
//                .userNameAttributeName("id") //Github 에서 사용자를 식별할 속성 이름을 설정
//                .clientName("Github") //OAuth 클라이언트 이름을 설정
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE) //인증 방식 설정(인증 코드)
//                .redirectUri("{baseUrl}/{action}/oauth2/code/{registrationId") //리디렉션 URI 설정
//                .build();

        //시큐리티에서 oauth 2 인증 공급자 설정을 간편하게 할 수 있는 클래스 제공한다. 공급자가 없다면 위와 같이 정의해야 한다.
        return CommonOAuth2Provider.GITHUB
                .getBuilder("github")
                .clientId("")
                .clientSecret("")
                .build();
    }

}
