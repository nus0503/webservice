package com.jojoldu.book.webservice.common.validation.file;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.IOException;
import java.io.InputStream;

/**
 * 업로드 파일의 유효성 검사 체크용
 *  - 사용 방법 예) @FileUploadValid(allowFileDefines = {UploadAllowFileDefine.CSV}, message = "유효한 CSV파일만 업로드 가능합니다.")
 */
@Slf4j
@Component
public class FileUploadValidator implements ConstraintValidator<FileUploadValid, MultipartFile> {

    private FileUploadValid annotation;

    @Override
    public void initialize(FileUploadValid constraintAnnotation) {
        annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {

        if (multipartFile.isEmpty()) {
            context.buildConstraintViolationWithTemplate("업로드 대상 파일이 없습니다. 정확히 선택 업로드해주세요.").addConstraintViolation();
            return false;
        }

        final String filename = multipartFile.getOriginalFilename();
        if (StringUtils.isBlank(filename)) {
            context.buildConstraintViolationWithTemplate("업로드 요청한 파일명이 존재하지 않습니다.").addConstraintViolation();
            return false;
        }

        try {
            int targetByte = multipartFile.getBytes().length;
            if (targetByte == 0) {
                context.buildConstraintViolationWithTemplate("파일의 용량이 0 byte입니다.").addConstraintViolation();
                return false;
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            context.buildConstraintViolationWithTemplate("파일의 용량 확인 중 에러가 발생했습니다.").addConstraintViolation();
            return false;
        }

        // 허용된 파일 확장자 검사
        final String detechedMediaType = this.getMimeTypeByTika(multipartFile);

        final UploadAllowFileDefine[] allowExtArray = annotation.allowFileDefine();
        final String fileExt = FilenameUtils.getExtension(filename);

        for (UploadAllowFileDefine allowDefine : allowExtArray) {

            // 파일명의 허용 확장자 검사
            if (StringUtils.equals(allowDefine.getFileExtensionLowerCase(), fileExt.toLowerCase()) == false) {
                StringBuilder sb = new StringBuilder();
                sb.append("허용되지 않는 확장자의 파일이며 다음 확장자들만 허용됩니다.");
                sb.append(": ");
                sb.append(ArrayUtils.toString(allowExtArray));
                context.buildConstraintViolationWithTemplate(sb.toString()).addConstraintViolation();

                return false;
            }

            // 파일 변조 업로드를 막기위한 mime타입 검사(예. exe파일을 csv로 확장자 변경하는 업로드를 막음)
            if (ArrayUtils.contains(allowDefine.getAllowMimeTypes(), detechedMediaType) == false) {
                StringBuilder sb = new StringBuilder();
                sb.append("확장자 변조 파일은 허용되지 않습니다.");
                context.buildConstraintViolationWithTemplate(sb.toString()).addConstraintViolation();

                return false;
            }
        }
        return true;
    }


    /**
     * apache Tika 라이브러리를 이용해서 파일의 mimeType을 가져옴
     * @param multipartFile
     * @return
     */
    private String getMimeTypeByTika(MultipartFile multipartFile) {

        try {
            Tika tika = new Tika();
            InputStream inputStream = multipartFile.getInputStream();
            String mimeType = tika.detect(inputStream);
            log.debug("업로드 요청된 파일 {}의 mimeType={}", multipartFile.getOriginalFilename(), mimeType);
            inputStream.close();
            return mimeType;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
