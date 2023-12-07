package com.jojoldu.book.webservice.service.users;

import com.jojoldu.book.webservice.domain.user.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        return usersRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원정보가 일치하지 않습니다."));
    }
}
