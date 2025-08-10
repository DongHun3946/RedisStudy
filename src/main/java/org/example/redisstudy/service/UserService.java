package org.example.redisstudy.service;

import lombok.RequiredArgsConstructor;
import org.example.redisstudy.dto.user.request.UserLoginRequest;
import org.example.redisstudy.dto.user.request.UserSignupRequest;
import org.example.redisstudy.entity.User;
import org.example.redisstudy.exception.custom.ExceptionCode;
import org.example.redisstudy.exception.global.BusinessException;
import org.example.redisstudy.repository.UserRepository;
import org.example.redisstudy.util.SnowflakeUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(UserSignupRequest request) {
        //TODO : 회원가입 로직
        if(userRepository.existsByUserId(request.userId())) {
            throw new BusinessException(ExceptionCode.USER_USERID_ALREADY_EXIST);
        }
        String encryptPassword = passwordEncoder.encode(request.password());
        User createdUser = User.create(SnowflakeUtil.nextId(), request.userId(), encryptPassword, request.nickname());
        userRepository.save(createdUser);
    }

    public Long login(UserLoginRequest request) {
        return userRepository.findByUserId(request.userId())
                .map(User::getId)
                .orElseThrow(() -> new BusinessException(ExceptionCode.USER_ACCOUNT_NOT_FOUND));
    }
}
