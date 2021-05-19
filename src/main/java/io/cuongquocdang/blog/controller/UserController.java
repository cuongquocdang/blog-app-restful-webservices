package io.cuongquocdang.blog.controller;

import io.cuongquocdang.blog.dto.response.UserResponseDto;
import io.cuongquocdang.blog.security.annotation.AdminPermission;
import io.cuongquocdang.blog.security.annotation.CurrentUser;
import io.cuongquocdang.blog.security.vo.UserPrincipal;
import io.cuongquocdang.blog.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static io.cuongquocdang.blog.constant.AppConstant.DEFAULT_PAGE;
import static io.cuongquocdang.blog.constant.AppConstant.DEFAULT_SIZE;
import static io.cuongquocdang.blog.controller.UserController.USER_API;
import static io.cuongquocdang.blog.util.PageUtil.getPagingResponse;

@RestController
@RequestMapping(USER_API)
@AllArgsConstructor
public class UserController {

    public static final String USER_API = "/api/users";

    private final UserService userService;

    @GetMapping
    @AdminPermission
    public ResponseEntity<?> getAllUsers(@RequestParam(value = "page", defaultValue = DEFAULT_PAGE) Integer page,
                                         @RequestParam(value = "size", defaultValue = DEFAULT_SIZE) Integer size) {
        Page<UserResponseDto> users = userService.findAll(page, size);
        return new ResponseEntity<>(getPagingResponse(users), HttpStatus.OK);
    }

    @GetMapping("{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username, @CurrentUser UserPrincipal currentUser) {
        return new ResponseEntity<>(userService.findByUsername(username, currentUser), HttpStatus.OK);
    }

}
