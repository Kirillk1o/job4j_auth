package ru.job4j.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;

import java.util.List;
@Data
@AllArgsConstructor
public class ErrorResponse {

    private HttpStatus status;

    private String message;

    private List<ObjectError> errors;
}
