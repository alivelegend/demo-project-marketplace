package com.example.demoprojectmarketplace.exception.custom;

import com.example.demoprojectmarketplace.exception.HttpResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice//Следит что происходит в контроллерах
public class ExceptionHandling {

    @ExceptionHandler(CustomShowMessageException.class)//Аннотация сверяет ошибку
    public ResponseEntity<HttpResponseException> CustomShowMessageException(CustomShowMessageException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(UnexpectedException.class)//Аннотация сверяет ошибку
    public ResponseEntity<HttpResponseException> UnexpectedException(UnexpectedException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)//Аннотация сверяет ошибку
    public ResponseEntity<HttpResponseException> NotFoundException(NotFoundException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(RepositoryCreateException.class)//Аннотация сверяет ошибку
    public ResponseEntity<HttpResponseException> RepositoryCreateException(RepositoryCreateException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(RepositoryUpdateException.class)//Аннотация сверяет ошибку
    public ResponseEntity<HttpResponseException> RepositoryUpdateException(RepositoryUpdateException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(RepositoryDeleteException.class)//Аннотация сверяет ошибку
    public ResponseEntity<HttpResponseException> RepositoryDeleteException(RepositoryDeleteException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HttpResponseException> MethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        if (exception.hasErrors()) {
            return createHttpResponse(BAD_REQUEST, exception.getFieldError().getDefaultMessage());
        }
        return null;

    }

    private ResponseEntity<HttpResponseException> createHttpResponse(HttpStatus httpStatus, String message) {

        return new ResponseEntity<>(new HttpResponseException(httpStatus.value(), httpStatus
                , httpStatus.getReasonPhrase().toUpperCase(), message), httpStatus);

    }
}
