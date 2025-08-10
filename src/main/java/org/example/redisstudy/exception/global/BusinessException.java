package org.example.redisstudy.exception.global;

import lombok.Getter;
import org.example.redisstudy.exception.custom.ExceptionCode;

@Getter
public class BusinessException extends RuntimeException{

    private final ExceptionCode exceptionCode;
    private final Object context;

    public BusinessException(ExceptionCode exceptionCode) { this(exceptionCode, null); }

    public BusinessException(ExceptionCode exceptionCode, Object context) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
        this.context = context;
    }
}
