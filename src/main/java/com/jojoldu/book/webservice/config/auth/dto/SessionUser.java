package com.jojoldu.book.webservice.config.auth.dto;

import com.jojoldu.book.webservice.domain.oAuthUser.Role;
import com.jojoldu.book.webservice.domain.oAuthUser.User;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SessionUser implements Serializable {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String picture;
    private Role role;
    private String modifiedDate;

    public SessionUser(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.picture = user.getPicture();
        this.role = user.getRole();
        this.modifiedDate = user.getModifiedDate();
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
