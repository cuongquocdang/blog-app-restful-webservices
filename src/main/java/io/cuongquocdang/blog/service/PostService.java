package io.cuongquocdang.blog.service;

import io.cuongquocdang.blog.dto.request.PostRequestDto;
import io.cuongquocdang.blog.dto.response.PostResponseDto;
import org.springframework.data.domain.Page;

public interface PostService {

    Page<PostResponseDto> findAll(Integer page, Integer size);

    PostResponseDto create(PostRequestDto request);

    PostResponseDto update(Long postId, PostRequestDto request);

    void delete(Long postId);
}
