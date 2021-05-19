package io.cuongquocdang.blog.security.service.impl;

import io.cuongquocdang.blog.entity.UserEntity;
import io.cuongquocdang.blog.exception.ResourceNotFoundException;
import io.cuongquocdang.blog.repository.UserRepository;
import io.cuongquocdang.blog.security.vo.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final String NOT_FOUND_BY_USERNAME_FORMAT = "Not found by %s";

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(NOT_FOUND_BY_USERNAME_FORMAT, username)));
        return new UserPrincipal(user);
    }
}
