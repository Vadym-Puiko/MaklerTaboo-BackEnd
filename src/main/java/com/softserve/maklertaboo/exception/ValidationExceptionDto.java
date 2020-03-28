package com.softserve.maklertaboo.exception;

import lombok.Data;
import org.springframework.validation.FieldError;

import java.io.Serializable;

@Data
public class ValidationExceptionDto implements Serializable {
    private String name;
    private String message;

    public ValidationExceptionDto(FieldError error) {
        this.name = error.getField();
        this.message = error.getDefaultMessage();
    }

    public ValidationExceptionDto(String name, String message) {
        this.name = name;
        this.message = message;
    }
}

