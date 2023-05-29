package com.sociallogin.service;

import com.sociallogin.config.SocialUserDetail;
import com.sociallogin.entity.SocialUser;
import com.sociallogin.model.Role;
import com.sociallogin.repository.SocialUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@Transactional
public class SocialUserService extends DefaultOAuth2UserService {

    @Autowired
    private SocialUserRepository socialUserRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        Optional<SocialUser> findSocialUser = socialUserRepository.findByEmailAndProvider(email, provider);

        if(findSocialUser.isEmpty()){
            SocialUser socialUser = SocialUser.builder()
                    .email(email)
                    .name(name)
                    .role(Role.USER)
                    .provider(provider)
                    .build();
            socialUserRepository.save(socialUser);
            return new SocialUserDetail(socialUser, oAuth2User.getAttributes());
        }

        return new SocialUserDetail(findSocialUser.get(), oAuth2User.getAttributes());
    }
}
