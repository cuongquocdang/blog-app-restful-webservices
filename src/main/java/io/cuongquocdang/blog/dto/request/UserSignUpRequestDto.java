package io.cuongquocdang.blog.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class UserSignUpRequestDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;

    private String email;

    private String password;

    private String passwordVerification;

    private String fullName;

    private String phoneNumber;

    private LocalDate dateOfBirth;

}
