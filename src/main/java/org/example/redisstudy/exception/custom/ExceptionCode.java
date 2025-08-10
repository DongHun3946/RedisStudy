package org.example.redisstudy.exception.custom;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ExceptionCode {

    USER_ACCOUNT_NOT_FOUND(NOT_FOUND, "계정이 존재하지 않습니다. 다시 시도해주세요."),
    USER_USERID_ALREADY_EXIST(BAD_REQUEST, "이미 존재하는 아이디입니다.");

    private final HttpStatus status;
    private final String message;
    ExceptionCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
