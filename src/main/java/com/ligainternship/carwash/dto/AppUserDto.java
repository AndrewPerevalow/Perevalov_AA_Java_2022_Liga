package com.ligainternship.carwash.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
public class AppUserDto implements UserDetails {

    private Long id;
    private String login;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private UserDetails user;

    public AppUserDto(UserDetails user) {
        this.user = user;
        this.login =  user.getUsername();
        this.password = user.getPassword();
        this.authorities = user.getAuthorities();

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
