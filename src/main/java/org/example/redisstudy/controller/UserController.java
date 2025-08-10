package org.example.redisstudy.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.redisstudy.dto.user.request.UserLoginRequest;
import org.example.redisstudy.dto.user.request.UserSignupRequest;
import org.example.redisstudy.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signupUser(@Valid @RequestBody UserSignupRequest request) {
        userService.signUp(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginRequest request,
                                       HttpServletRequest httpRequest) {
        Long id = userService.login(request);
        HttpSession session = httpRequest.getSession();
        session.setAttribute("userId", id);
        session.setMaxInactiveInterval(3600); //초 단위 3600초 = 1시간

        return ResponseEntity.ok().build();
    }

    @PostMapping("logout")
    public ResponseEntity<?> logoutUser() {
        return null;
    }
}
