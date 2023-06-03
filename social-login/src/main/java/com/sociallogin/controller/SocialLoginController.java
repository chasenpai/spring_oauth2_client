package com.sociallogin.controller;

import com.sociallogin.config.SocialUserDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class SocialLoginController {

    @GetMapping("/social-login")
    public String socialLogin(){
        return "login";
    }

    @GetMapping("/social-user")
    public String socialUser(@AuthenticationPrincipal SocialUserDetail loginUser){
        System.out.println(loginUser.getAttributes());
        return "user";
    }

}
