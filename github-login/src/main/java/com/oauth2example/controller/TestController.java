package com.oauth2example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @GetMapping("/")
    public String test(OAuth2AuthenticationToken token){
        log.info(String.valueOf(token.getPrincipal()));
        return "test";
    }

}
