package org.example.redisstudy.dto.user.request;

public record UserLoginRequest(

        String userId,
        String password
) {
}
