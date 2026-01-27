package com.e_com.e_com_spring.security;

import com.e_com.e_com_spring.model.User;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class CustomUserDetails implements UserDetails {
    private User user;

    public User getUser() {
        return user;
    }

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        log.info("INSIDE getAuthorities METHOD");
        List<GrantedAuthority> authorities = new ArrayList<>();
        log.info("ADD ROLE AUTHORITY: {}", user.getRole());
        authorities.add(new SimpleGrantedAuthority(user.getRole().getRoleType().name()));
        log.info("ADD ALL AUTHORITIES");
        authorities.addAll(
                user.getRole()
                        .getPrivileges()
                        .stream()
                        .map(privilege -> new SimpleGrantedAuthority(privilege.getName()))
                        .toList()
        );
        log.info("RETURN AUTHORITIES");
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
