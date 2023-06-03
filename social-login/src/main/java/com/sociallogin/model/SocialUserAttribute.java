package com.sociallogin.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.thymeleaf.util.StringUtils;

import java.util.Map;

@Getter
@NoArgsConstructor
public class SocialUserAttribute {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;

    public static SocialUserAttribute of(String provider, String userNameAttributeName, Map<String, Object> attributes){

        if(StringUtils.equals(provider, "naver")){
            return ofNaver(userNameAttributeName, attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static SocialUserAttribute ofGoogle(String userNameAttributeName, Map<String, Object> attributes){
        return SocialUserAttribute.builder()
                .name(String.valueOf(attributes.get("name")))
                .email(String.valueOf(attributes.get("email")))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static SocialUserAttribute ofNaver(String userNameAttributeName, Map<String, Object> attributes){
        Map<String, Object> response = getMap(attributes, userNameAttributeName);
        return SocialUserAttribute.builder()
                .name(getMapValue(response, "name"))
                .email(getMapValue(response, "email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static Map<String, Object> getMap(Map<String, Object> map, String key){
        return (Map<String, Object>) map.get(key);
    }

    private static String getMapValue(Map<String, Object> map, String key){
        return String.valueOf(map.get(key));
    }

    @Builder
    public SocialUserAttribute(Map<String, Object> attributes, String nameAttributeKey, String name, String email) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
    }

}
