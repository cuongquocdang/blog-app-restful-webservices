package io.cuongquocdang.blog.controller;

import io.cuongquocdang.blog.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.cuongquocdang.blog.controller.CommentController.COMMENT_API;

@RestController
@RequestMapping(COMMENT_API)
@AllArgsConstructor
public class CommentController {

    public static final String COMMENT_API = "/api/comments";

    private final CommentService commentService;

}
