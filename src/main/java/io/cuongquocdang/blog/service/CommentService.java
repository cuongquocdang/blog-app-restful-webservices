package io.cuongquocdang.blog.service;

import io.cuongquocdang.blog.dto.request.CommentRequestDto;
import io.cuongquocdang.blog.dto.response.CommentResponseDto;
import org.springframework.data.domain.Page;

public interface CommentService {

    Page<CommentResponseDto> findAllByPostId(Long postId, Integer page, Integer size);

    CommentResponseDto create(Long postId, CommentRequestDto request);

    CommentResponseDto update(Long postId, Long commentId,  CommentRequestDto request);

    void delete(Long postId, Long commentId);
}
