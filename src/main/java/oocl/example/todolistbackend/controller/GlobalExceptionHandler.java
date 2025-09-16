package oocl.example.todolistbackend.controller;

import oocl.example.todolistbackend.exception.TodoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public Map<String,String> handleValidationException(MethodArgumentNotValidException ex) {
        return Map.of("message", ex.getMessage());
    }

    @ExceptionHandler(TodoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String,String> handleTodoNotFoundException(TodoNotFoundException ex) {
        return Map.of("message", ex.getMessage());
    }




}
