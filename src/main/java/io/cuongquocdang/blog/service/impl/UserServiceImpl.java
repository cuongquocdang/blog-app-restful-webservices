package io.cuongquocdang.blog.service.impl;

import io.cuongquocdang.blog.dto.response.UserResponseDto;
import io.cuongquocdang.blog.entity.UserEntity;
import io.cuongquocdang.blog.exception.ResourceNotFoundException;
import io.cuongquocdang.blog.repository.UserRepository;
import io.cuongquocdang.blog.security.vo.UserPrincipal;
import io.cuongquocdang.blog.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String RESOURCE_NOT_FOUND_FORMAT = "Not found with %s.";

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Override
    public Page<UserResponseDto> findAll(Integer page, Integer size) {
        Pageable paging = PageRequest.of(page, size);
        Page<UserEntity> users = userRepository.findAll(paging);
        return convertPageEntityToPageDto(users);
    }

    private Page<UserResponseDto> convertPageEntityToPageDto(Page<UserEntity> users) {
        return users.map(this::convertEntityToResponseDto);
    }

    private UserResponseDto convertEntityToResponseDto(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserResponseDto.class);
    }

    @Override
    public UserResponseDto findByUsername(String username, UserPrincipal currentUser) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(RESOURCE_NOT_FOUND_FORMAT, username)));
        return modelMapper.map(user, UserResponseDto.class);
    }
}
