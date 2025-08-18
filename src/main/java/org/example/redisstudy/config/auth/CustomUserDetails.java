package org.example.redisstudy.config.auth;

import lombok.Getter;
import org.example.redisstudy.entity.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    @Getter
    private final Long id;

    @Getter
    private final String userId;

    private final String password;

    @Getter
    private final String nickname;

    @Getter
    private final boolean isUsed;

    @Getter
    private final boolean isDeleted;

    @Getter
    private final Role role;

    public CustomUserDetails(Long id, String userId, String password, String nickname, boolean isUsed, boolean isDeleted, Role role) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
        this.isUsed = isUsed;
        this.isDeleted = isDeleted;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userId;
    }
}
