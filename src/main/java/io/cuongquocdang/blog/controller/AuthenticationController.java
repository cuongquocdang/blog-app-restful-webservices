package io.cuongquocdang.blog.controller;

import io.cuongquocdang.blog.dto.request.UserSignInRequestDto;
import io.cuongquocdang.blog.dto.request.UserSignUpRequestDto;
import io.cuongquocdang.blog.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static io.cuongquocdang.blog.controller.AuthenticationController.AUTHENTICATION_API;

@RestController
@RequestMapping(AUTHENTICATION_API)
@AllArgsConstructor
public class AuthenticationController {

    public static final String AUTHENTICATION_API = "/api/auth";

    private final AuthenticationService authenticationService;

    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@RequestBody @Valid UserSignInRequestDto request) {
        return ResponseEntity.ok(authenticationService.signIn(request));
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody @Valid UserSignUpRequestDto request) {
        return ResponseEntity.ok(authenticationService.signUp(request));
    }


}
