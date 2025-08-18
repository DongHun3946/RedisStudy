package org.example.redisstudy.service;

import lombok.RequiredArgsConstructor;
import org.example.redisstudy.dto.user.request.UserLoginRequest;
import org.example.redisstudy.dto.user.request.UserSignupRequest;
import org.example.redisstudy.entity.User;
import org.example.redisstudy.entity.enums.Role;
import org.example.redisstudy.exception.custom.ExceptionCode;
import org.example.redisstudy.exception.global.BusinessException;
import org.example.redisstudy.repository.UserRepository;
import org.example.redisstudy.util.SnowflakeUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public void signUp(UserSignupRequest request) {
        //TODO : 회원가입 로직
        if(userRepository.existsByUserId(request.userId())) {
            throw new BusinessException(ExceptionCode.USER_USERID_ALREADY_EXIST);
        }
        String encryptPassword = passwordEncoder.encode(request.password());
        User createdUser = User.create(
                SnowflakeUtil.nextId(),
                request.userId(),
                encryptPassword,
                request.nickname(),
                Role.USER
        );
        userRepository.save(createdUser);
    }

    public Long login(UserLoginRequest request) {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(request.userId(), request.password());
        //인증 수행
        Authentication authentication = authenticationManager.authenticate(authToken);

        //인증된 authentication 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return null;
    }
}
