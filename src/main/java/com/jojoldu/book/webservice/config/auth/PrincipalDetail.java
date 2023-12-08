package com.jojoldu.book.webservice.config.auth;

import com.jojoldu.book.webservice.domain.oAuthUser.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;


import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
public class PrincipalDetail implements UserDetails {

    private User user;

    public PrincipalDetail(User user) {
        this.user = user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(() -> user.getRoleKey());
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
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
