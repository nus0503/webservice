package com.jojoldu.book.webservice.domain.oAuthUser;

import com.jojoldu.book.webservice.domain.BaseTimeEntity;
import com.jojoldu.book.webservice.domain.userImage.UserImage;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private String picture;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Setter
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "userImage_id")
    private UserImage userImage;

    @Builder
    public User(String name, String email, String password, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.picture = picture;
        this.role = role;
    }

    public User(String name, String email, String password, String picture, Role role, UserImage userImage) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.picture = picture;
        this.role = role;
        this.userImage = userImage;
    }

    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }

    public void modify(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }


}
