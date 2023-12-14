package com.jojoldu.book.webservice.config.auth;

import com.jojoldu.book.webservice.config.auth.dto.SessionUser;
import com.jojoldu.book.webservice.domain.oAuthUser.User;
import com.jojoldu.book.webservice.domain.oAuthUser.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
/**
 * UserDetailsService 인터페이스를 구현하고, loadUserByUsername() 메소드를 오버라이딩해서 시큐리티에서
 * /login을 했을 때 사용자 정보를 가져오는 로직 작성
 */
@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private final HttpSession httpSession;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("회원정보가 일치하지 않습니다. : " + email));
        httpSession.setAttribute("user", new SessionUser(user));
        return new PrincipalDetail(user);
    }
}
