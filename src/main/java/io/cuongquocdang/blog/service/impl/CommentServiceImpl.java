package io.cuongquocdang.blog.service.impl;

import io.cuongquocdang.blog.dto.request.CommentRequestDto;
import io.cuongquocdang.blog.dto.response.CommentResponseDto;
import io.cuongquocdang.blog.entity.CommentEntity;
import io.cuongquocdang.blog.entity.PostEntity;
import io.cuongquocdang.blog.exception.ResourceNotFoundException;
import io.cuongquocdang.blog.repository.CommentRepository;
import io.cuongquocdang.blog.repository.PostRepository;
import io.cuongquocdang.blog.service.CommentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    private final ModelMapper modelMapper;

    @Override
    public Page<CommentResponseDto> findAllByPostId(Long postId, Integer page, Integer size) {
        Pageable paging = PageRequest.of(page, size);
        Page<CommentEntity> comments = commentRepository.findAll(paging);
        return convertToPageDto(comments);
    }

    private Page<CommentResponseDto> convertToPageDto(Page<CommentEntity> post) {
        return post.map(this::convertToResponse);
    }

    @Override
    public CommentResponseDto create(Long postId, CommentRequestDto request) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(""));
        CommentEntity comment = convertToEntity(request);
        // set PostEntity
        comment.setPost(post);
        return convertToResponse(commentRepository.save(comment));
    }

    @Override
    public CommentResponseDto update(Long postId, Long commentId, CommentRequestDto request) {

        if(!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("");
        }

        return commentRepository.findById(commentId)
                .map(commentEntity -> convertToResponse(commentRepository.save(convertToEntity(request))))
                .orElseThrow(() -> new ResourceNotFoundException(""));
    }

    private CommentEntity convertToEntity(CommentRequestDto request) {
        return modelMapper.map(request, CommentEntity.class);
    }

    private CommentResponseDto convertToResponse(CommentEntity entity) {
        return modelMapper.map(entity, CommentResponseDto.class);
    }

    @Override
    public void delete(Long postId, Long commentId) {
        CommentEntity comment = commentRepository.findByIdAndPostId(commentId, postId)
                .orElseThrow(() -> new ResourceNotFoundException(""));

        commentRepository.delete(comment);
    }

}
