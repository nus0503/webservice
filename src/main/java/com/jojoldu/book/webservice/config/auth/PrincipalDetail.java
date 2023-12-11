package com.jojoldu.book.webservice.config.auth;

import com.jojoldu.book.webservice.domain.oAuthUser.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;


import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * 스프링 시큐리티가 로그인 요청을 가로채 로그인을 진행하고 완료 되면 UserDetails 타입의 오브젝트를 스프링 시큐리티의 고유한 세션저장소에 저장 해준다.
 */
@Getter
@Setter
public class PrincipalDetail implements UserDetails {

    private User user;

    public PrincipalDetail(User user) {
        this.user = user;
    }

    /* 유저의 권한 목록 */
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

    /**
     * 계정 활성화 여부
     * true : 만료 안됨
     * false :만료
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 계정 활성화 여부
     * true : 만료 안됨
     * false :만료
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 비밀번호 활성화 여부
     * true : 만료 안됨
     * false :만료
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 사용자 활성화 여부
     * true : 만료 안됨
     * false :만료
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
