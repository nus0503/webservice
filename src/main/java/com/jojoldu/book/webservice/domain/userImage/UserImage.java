package com.jojoldu.book.webservice.domain.userImage;

import com.jojoldu.book.webservice.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class UserImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String fileName;

    @Column
    private String filePath;

    @Column
    private String fileType;

    @Column
    private Long fileSize;

    @Column
    private String uploadUser;

    @Builder
    public UserImage(Long id, String fileName, String filePath, String fileType, Long fileSize, String uploadUser) {
        this.id = id;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.uploadUser = uploadUser;
    }
}
