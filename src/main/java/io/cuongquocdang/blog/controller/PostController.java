package io.cuongquocdang.blog.controller;

import io.cuongquocdang.blog.dto.request.PostRequestDto;
import io.cuongquocdang.blog.dto.response.PostResponseDto;
import io.cuongquocdang.blog.security.annotation.UserPermission;
import io.cuongquocdang.blog.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static io.cuongquocdang.blog.constant.AppConstant.DEFAULT_PAGE;
import static io.cuongquocdang.blog.constant.AppConstant.DEFAULT_SIZE;
import static io.cuongquocdang.blog.controller.PostController.POST_API;
import static io.cuongquocdang.blog.util.PageUtil.getPagingResponse;

@RestController
@RequestMapping(POST_API)
@AllArgsConstructor
public class PostController {

    public static final String POST_API = "/api/posts";

    private final PostService postService;

    @GetMapping
    @UserPermission
    public ResponseEntity<?> getAllPosts(@RequestParam(value = "page", defaultValue = DEFAULT_PAGE) Integer page,
                                         @RequestParam(value = "size", defaultValue = DEFAULT_SIZE) Integer size) {
        Page<PostResponseDto> posts = postService.findAll(page, size);
        return new ResponseEntity<>(getPagingResponse(posts), HttpStatus.OK);
    }

    @PostMapping
    @UserPermission
    public ResponseEntity<?> createPost(@Valid @RequestBody PostRequestDto request) {
        return ResponseEntity.ok(postService.create(request));

    }

    @PutMapping("{postId}")
    @UserPermission
    public ResponseEntity<?> updatePost(@PathVariable Long postId, @Valid @RequestBody PostRequestDto request) {
        return ResponseEntity.ok(postService.update(postId, request));
    }

    @DeleteMapping("{postId}")
    @UserPermission
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        postService.delete(postId);
        return ResponseEntity.ok().build();
    }
}
