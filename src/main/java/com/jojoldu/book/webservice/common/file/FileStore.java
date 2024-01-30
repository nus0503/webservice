package com.jojoldu.book.webservice.common.file;

import com.jojoldu.book.webservice.controller.user.dto.UserImageDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Component
public class FileStore {


    public static String fileDir;

    @Value("${file.dir}")
    public void setFileDir(String value) {
        fileDir = value;
    }

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    //확장자를 가져오는 메서드
    public static String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    //파일 이름만 가져오는 메서드
    public static String getFileName(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    //UUID값을 파일이름에 붙여줘서 가져와주는 메서드
    public static String getFileNameWithUUID(String fileName) {
        return UUID.randomUUID().toString() + "_" + fileName;
    }

    //정적 팩토리 메서드
    public static File createFile(String uploadPath, String fileName) {
        return new File(uploadPath, fileName);
    }

    public static File getMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(fileDir, getFileNameWithUUID(multipartFile.getOriginalFilename()));
        multipartFile.transferTo(file);
        return file;
    }

    public static File getFileFromFileDomain(UserImageDto dto) {
        return new File(dto.getFilePath());
    }

    public static void deleteFile(UserImageDto dto) {
        File file = getFileFromFileDomain(dto);
        if (file.exists()) {
            file.delete();
        }
    }

    public static UserImageDto getFileDtoFromMultipartFile(MultipartFile multipartFile, String uploadUser) throws IOException {
        File file = getMultipartFileToFile(multipartFile);
        String fileName = file.getName();
        String fileType = getExtension(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        Long fileSize = multipartFile.getSize();
        return UserImageDto.builder()
                .fileName(fileName)
                .filePath(fileDir + fileName)
                .fileType(fileType)
                .fileSize(fileSize)
                .uploadUser(uploadUser)
                .build();
    }

}
