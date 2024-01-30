package com.jojoldu.book.webservice.controller.user.dto;

import com.jojoldu.book.webservice.domain.userImage.UserImage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserImageDto {
    private Long id;
    private String fileName;

    private String filePath;

    private String fileType;

    private Long fileSize;

    private String uploadUser;

    public UserImageDto(Long id, String fileName, String filePath, String fileType, Long fileSize, String uploadUser) {
        this.id = id;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.uploadUser = uploadUser;
    }

    public static UserImageDto from(UserImage userImage) {
        return UserImageDto.builder()
                .id(userImage.getId())
                .fileName(userImage.getFileName())
                .filePath(userImage.getFilePath())
                .fileType(userImage.getFileType())
                .fileSize(userImage.getFileSize())
                .uploadUser(userImage.getUploadUser())
                .build();
    }

    public UserImage toEntity() {
        return UserImage.builder()
                .id(id)
                .fileName(fileName)
                .filePath(filePath)
                .fileType(fileType)
                .fileSize(fileSize)
                .uploadUser(uploadUser)
                .build();
    }
}
