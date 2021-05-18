package io.cuongquocdang.blog.exception;

import io.cuongquocdang.blog.exception.vo.RestErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
public class RestGlobalException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = BadCredentialException.class)
    protected ResponseEntity<RestErrorResponse> handleBadCredentialException(BadCredentialException ex, WebRequest request) {
        return RestErrorResponse.builder()
                .status(UNAUTHORIZED)
                .message(ex.getMessage())
                .path(getPath(request))
                .entity();
    }

    @ExceptionHandler(ResponseStatusException.class)
    ResponseEntity<?> handleStatusException(ResponseStatusException ex, WebRequest request) {
        logger.error(ex.getReason(), ex);
        return handleResponseStatusException(ex, request);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<?> handleAllExceptions(Exception ex, WebRequest request) {
        logger.error(ex.getLocalizedMessage(), ex);
        return handleEveryException(ex, request);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                                       HttpStatus status, WebRequest request) {

        ResponseEntity<?> responseEntity;

        if (!status.isError()) {
            responseEntity = handleStatusException(ex, status, request);
        } else if (INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute("javax.servlet.error.exception", ex, 0);
            responseEntity = handleEveryException(ex, request);
        } else {
            responseEntity = handleEveryException(ex, request);
        }

        return (ResponseEntity<Object>) responseEntity;
    }

    protected ResponseEntity<RestErrorResponse> handleResponseStatusException(ResponseStatusException ex, WebRequest request) {
        return RestErrorResponse.builder()
                .exception(ex)
                .path(getPath(request))
                .entity();
    }

    protected ResponseEntity<RestErrorResponse> handleStatusException(Exception ex, HttpStatus status, WebRequest request) {
        return RestErrorResponse.builder()
                .status(status)
                .message("Execution halted")
                .path(getPath(request))
                .entity();
    }

    protected ResponseEntity<RestErrorResponse> handleEveryException(Exception ex, WebRequest request) {
        return RestErrorResponse.builder()
                .status(INTERNAL_SERVER_ERROR.value())
                .message("Server encountered an error")
                .path(getPath(request))
                .entity();
    }

    private String getPath(WebRequest request) {
        return request.getDescription(false).substring(4);
    }

}
