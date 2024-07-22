package com.sparta.bookflex.domain.user.controller;

import com.sparta.bookflex.common.config.JwtConfig;
import com.sparta.bookflex.common.dto.CommonDto;
import com.sparta.bookflex.common.jwt.JwtProvider;
import com.sparta.bookflex.common.security.UserDetailsImpl;
import com.sparta.bookflex.domain.user.dto.LoginReqDto;
import com.sparta.bookflex.domain.user.dto.SignUpReqDto;
import com.sparta.bookflex.domain.user.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpReqDto signupReqDto) {
        authService.signUp(signupReqDto);
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginReqDto loginReqDto, HttpServletResponse response) {
        List<String> tokens = authService.login(loginReqDto);
        response.addHeader(JwtConfig.ACCESS_TOKEN_HEADER, tokens.get(0));
        response.addHeader(JwtConfig.REFRESH_TOKEN_HEADER,tokens.get(1));

        return ResponseEntity.ok().body(null);
    }

    @PutMapping("/signout")
    public ResponseEntity<Void> signOut(@AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response){

        authService.signOut(userDetails.getUser());
        response.setHeader(JwtConfig.ACCESS_TOKEN_HEADER, "");
        response.setHeader(JwtConfig.REFRESH_TOKEN_HEADER, "");

        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response){
        authService.logout(userDetails.getUser());
        response.setHeader(JwtConfig.ACCESS_TOKEN_HEADER, "");
        response.setHeader(JwtConfig.REFRESH_TOKEN_HEADER, "");

        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Void> refreshToken(@AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response, HttpServletRequest request)
    {
        String accessToken = authService.refreshToken(userDetails.getUser(), request.getHeader(JwtConfig.AUTHORIZATION_HEADER));
        response.setHeader(JwtConfig.ACCESS_TOKEN_HEADER, accessToken);

        return ResponseEntity.ok().body(null);
    }
}