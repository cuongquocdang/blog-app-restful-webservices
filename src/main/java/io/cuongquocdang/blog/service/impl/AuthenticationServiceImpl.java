package io.cuongquocdang.blog.service.impl;

import io.cuongquocdang.blog.dto.request.UserSignInRequestDto;
import io.cuongquocdang.blog.dto.request.UserSignUpRequestDto;
import io.cuongquocdang.blog.dto.response.UserSignInResponseDto;
import io.cuongquocdang.blog.dto.response.UserSignUpResponseDto;
import io.cuongquocdang.blog.entity.RoleEntity;
import io.cuongquocdang.blog.entity.UserEntity;
import io.cuongquocdang.blog.enumeration.RoleName;
import io.cuongquocdang.blog.exception.BadCredentialException;
import io.cuongquocdang.blog.exception.ResourceNotFoundException;
import io.cuongquocdang.blog.repository.RoleRepository;
import io.cuongquocdang.blog.repository.UserRepository;
import io.cuongquocdang.blog.security.vo.UserPrincipal;
import io.cuongquocdang.blog.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;

import static io.cuongquocdang.blog.security.util.TokenUtil.generateJwtToken;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final String BAD_CREDENTIAL = "Bad Credential.";

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    @Override
    public UserSignInResponseDto signIn(UserSignInRequestDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        String jwt = generateJwtToken(authentication);

        return convertToUserResponse(userPrincipal.getUser(), jwt);

    }

    private UserSignInResponseDto convertToUserResponse(UserEntity userEntity, String jwtToken) {
        UserSignInResponseDto userResponse = modelMapper.map(userEntity, UserSignInResponseDto.class);
        userResponse.setJwtToken(jwtToken);
        return userResponse;
    }

    @Override
    public UserSignUpResponseDto signUp(UserSignUpRequestDto request) {
        validateMatchingPasswordAndPasswordVerification(request.getPassword(), request.getPasswordVerification());
        validateExistingUsernameOrEmail(request.getUsername(), request.getEmail());
        UserEntity user = modelMapper.map(request, UserEntity.class);
        // encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        RoleEntity role = roleRepository.findByName(RoleName.USER)
                .orElseThrow(() -> new ResourceNotFoundException("User Role not set."));
        user.setRoles(Collections.singleton(role));
        UserEntity userResult = userRepository.save(user);
        return modelMapper.map(userResult, UserSignUpResponseDto.class);
    }

    private void validateExistingUsernameOrEmail(String username, String email) {
        if (userRepository.existsByUsernameOrEmail(username, email)) {
            throw new BadCredentialException(BAD_CREDENTIAL);
        }
    }

    private void validateMatchingPasswordAndPasswordVerification(String password, String passwordVerification) {
        if (!Objects.equals(password, passwordVerification)) {
            throw new BadCredentialException(BAD_CREDENTIAL);
        }
    }


}
