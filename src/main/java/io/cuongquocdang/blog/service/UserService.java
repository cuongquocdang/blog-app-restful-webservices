package io.cuongquocdang.blog.service;

import io.cuongquocdang.blog.dto.response.UserResponseDto;
import io.cuongquocdang.blog.security.vo.UserPrincipal;
import org.springframework.data.domain.Page;

public interface UserService {

    Page<UserResponseDto> findAll(Integer page, Integer size);

    UserResponseDto findByUsername(String username, UserPrincipal currentUser);

}
