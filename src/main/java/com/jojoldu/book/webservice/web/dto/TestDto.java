package com.jojoldu.book.webservice.web.dto;

import com.jojoldu.book.webservice.common.validation.file.FileUploadValid;
import com.jojoldu.book.webservice.common.validation.file.UploadAllowFileDefine;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class TestDto {
    @FileUploadValid(allowFileDefine = {UploadAllowFileDefine.IMG}, message = "유효한 img파일만 업로드 가능합니다.")
    private MultipartFile imgFile;
}
