package io.cuongquocdang.blog.service.impl;

import io.cuongquocdang.blog.dto.request.PostRequestDto;
import io.cuongquocdang.blog.dto.response.PostResponseDto;
import io.cuongquocdang.blog.entity.PostEntity;
import io.cuongquocdang.blog.exception.ResourceNotFoundException;
import io.cuongquocdang.blog.repository.PostRepository;
import io.cuongquocdang.blog.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static io.cuongquocdang.blog.util.ConvertUtil.*;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private static final String POST_NOT_FOUND_MESSAGE_FORMAT = "Not found post by %s.";

    private final PostRepository postRepository;

    @Override
    public Page<PostResponseDto> findAll(Integer page, Integer size) {
        Pageable paging = PageRequest.of(page, size);
        Page<PostEntity> posts = postRepository.findAll(paging);
        return convertPageEntityToPageResponse(posts, PostResponseDto.class);
    }

    @Override
    public PostResponseDto create(PostRequestDto request) {
        return convertEntityToResponse(postRepository.save(convertRequestToEntity(request, PostEntity.class)), PostResponseDto.class);
    }

    @Override
    public PostResponseDto update(Long postId, PostRequestDto request) {
        return postRepository.findById(postId)
                .map(postEntity -> convertEntityToResponse(postRepository.save(convertRequestToEntity(request, PostEntity.class)), PostResponseDto.class))
                .orElseThrow(() -> new ResourceNotFoundException(String.format(POST_NOT_FOUND_MESSAGE_FORMAT, postId)));
    }

    @Override
    public void delete(Long postId) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(POST_NOT_FOUND_MESSAGE_FORMAT, postId)));

        postRepository.delete(post);
    }
}
