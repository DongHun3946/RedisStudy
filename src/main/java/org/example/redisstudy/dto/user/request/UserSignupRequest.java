package org.example.redisstudy.dto.user.request;

public record UserSignupRequest(

        String userId,
        String password,
        String nickname
) {
}
