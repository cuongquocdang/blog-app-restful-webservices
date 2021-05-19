package io.cuongquocdang.blog.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostRequestDto {

    private String title;

    private String description;

    private String content;
}
