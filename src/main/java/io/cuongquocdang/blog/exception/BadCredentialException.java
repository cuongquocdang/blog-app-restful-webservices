package io.cuongquocdang.blog.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BadCredentialException extends RuntimeException {

    public BadCredentialException(String message) {
        super(message);
    }
}
