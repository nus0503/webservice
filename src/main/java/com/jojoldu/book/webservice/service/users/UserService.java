package com.jojoldu.book.webservice.service.users;

import com.jojoldu.book.webservice.domain.user.Users;
import com.jojoldu.book.webservice.domain.user.UsersRepository;
import com.jojoldu.book.webservice.web.dto.AddUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(AddUserRequest dto) {
        return usersRepository.save(new Users(
                dto.getEmail(),
                bCryptPasswordEncoder.encode(dto.getPassword())
        )).getId();
    }
}
