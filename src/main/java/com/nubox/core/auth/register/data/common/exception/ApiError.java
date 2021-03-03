package com.nubox.core.auth.register.data.common.exception;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ApiError {

    private Integer status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private Type type;
    private List<ApiSubError> errors;

    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    ApiError(Integer status) {
        this();
        this.status = status;
    }

    ApiError(Integer status, Type type) {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.type = type;
    }

    ApiError(Integer status, String message, Type type) {
        this();
        this.status = status;
        this.message = message;
        this.type = type;
    }

    ApiError(Integer status, String message, Type type, List<ApiSubError> errors) {
        this();
        this.status = status;
        this.message = message;
        this.type = type;
        this.errors = errors;
    }


    public enum Type {
        BUSINESS, TECHNICAL
    }
}

