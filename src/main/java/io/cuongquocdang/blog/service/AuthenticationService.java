package io.cuongquocdang.blog.service;

import io.cuongquocdang.blog.dto.request.UserSignInRequestDto;
import io.cuongquocdang.blog.dto.request.UserSignUpRequestDto;
import io.cuongquocdang.blog.dto.response.UserSignInResponseDto;
import io.cuongquocdang.blog.dto.response.UserSignUpResponseDto;

public interface AuthenticationService {

    UserSignInResponseDto signIn(UserSignInRequestDto request);

    UserSignUpResponseDto signUp(UserSignUpRequestDto request);

}
