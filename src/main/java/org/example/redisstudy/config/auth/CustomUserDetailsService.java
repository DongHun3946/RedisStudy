package org.example.redisstudy.config.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.redisstudy.entity.User;
import org.example.redisstudy.exception.custom.ExceptionCode;
import org.example.redisstudy.exception.global.BusinessException;
import org.example.redisstudy.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(id)
                .orElseThrow(() -> new BusinessException(ExceptionCode.USER_ACCOUNT_NOT_FOUND));

        return new CustomUserDetails(
                user.getId(),
                user.getUserId(),
                user.getPassword(),
                user.getNickname(),
                user.isUsed(),
                user.isDeleted(),
                user.getRole()
        );
    }
}
