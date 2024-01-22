package com.jojoldu.book.webservice.domain.userImage;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserImageRepository extends JpaRepository<UserImage, Long> {

    UserImage findByFileName(String fileName);
}
