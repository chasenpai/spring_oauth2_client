package com.sociallogin.service;

import com.sociallogin.config.SocialUserDetail;
import com.sociallogin.entity.SocialUser;
import com.sociallogin.model.Role;
import com.sociallogin.model.SocialUserAttribute;
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

        OAuth2User oAuth2User = super.loadUser(userRequest); //OAuth2User 정보를 가져오고 인증
        String provider = userRequest.getClientRegistration().getRegistrationId(); //공급자 추출
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails() //속성 이름 추출
                .getUserInfoEndpoint().getUserNameAttributeName();

        SocialUserAttribute socialUserAttribute = SocialUserAttribute
                .of(provider, userNameAttributeName, oAuth2User.getAttributes()); //공급자별로 사용자 정보 추출

        String email = socialUserAttribute.getEmail();
        String name = socialUserAttribute.getName();

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
