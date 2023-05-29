package com.sociallogin.entity;

import com.sociallogin.model.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "social_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SocialUser extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "provider", nullable = false)
    private String provider;

    @Builder
    public SocialUser(String email, String name, Role role, String provider) {
        this.email = email;
        this.name = name;
        this.role = role;
        this.provider = provider;
    }

}
