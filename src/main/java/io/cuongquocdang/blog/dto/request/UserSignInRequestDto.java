package io.cuongquocdang.blog.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class UserSignInRequestDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;

    private String password;

}
