package com.jojoldu.book.webservice.service.file;

import com.jojoldu.book.webservice.domain.userImage.UserImageRepository;
import com.jojoldu.book.webservice.web.dto.UserImageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FileService {

    private final UserImageRepository userImageRepository;

    public UserImageDto getFile(Long field) {
        log.info("getFile() field: {}", field);
        return userImageRepository.findById(field).map(UserImageDto::from).orElseThrow(() -> new EntityNotFoundException("파일이 없습니다 - field:" + field));
    }

    public void deleteFile(Long field) {
        log.info("deleteFile() field : {}", field);
        userImageRepository.deleteById(field);
    }

    public UserImageDto saveFile(UserImageDto dto) {
        log.info("saveFile() saveFile : {}", dto);
        return dto.from(userImageRepository.save(dto.toEntity()));
    }
}
