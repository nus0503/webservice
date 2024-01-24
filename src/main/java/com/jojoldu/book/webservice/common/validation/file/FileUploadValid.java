package com.jojoldu.book.webservice.common.validation.file;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 업로드 파일의 유효성 검사 체크용
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileUploadValidator.class)
public @interface FileUploadValid {

    String messsage() default "Invalid file type";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /** 업로드 허용 파일들의 정의 array(여러 종류의 파일 타입을 허용할 수도 있기에 array) */
    UploadAllowFileDefine[] allowFileDefine();




}
