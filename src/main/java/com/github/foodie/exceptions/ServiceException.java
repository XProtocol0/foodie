package com.github.foodie.exceptions;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class ServiceException extends RuntimeException{
    private final ServiceError serviceError;

    public ServiceError serviceError() {
        return this.serviceError;
    }

    public ServiceException(ServiceError serviceError) {
        super(serviceError.getMessage());
        this.serviceError = serviceError;
    }

    public static ServiceException genericRequest(HttpStatus status, String code, String message) {
        return new ServiceException(ServiceError
                .builder()
                .status(status.value())
                .message(message)
                .code(code)
                .build());
    }

    public static ServiceException badRequest(String code, String message) {
        return new ServiceException(ServiceError
                .builder()
                .status(BAD_REQUEST.value())
                .message(message)
                .code(code)
                .build());
    }

    public static ServiceException forbiddenRequest(String code, String message) {
        return new ServiceException(ServiceError
                .builder()
                .status(FORBIDDEN.value())
                .message(message)
                .code(code)
                .build());
    }

    public static ServiceException notFound(String code, String message) {
        return new ServiceException(ServiceError
                .builder()
                .status(NOT_FOUND.value())
                .message(message)
                .code(code)
                .build());
    }

    public static ServiceException externalError(String code, String message) {
        return new ServiceException(ServiceError
                .builder()
                .status(FAILED_DEPENDENCY.value())
                .message(message)
                .code(code)
                .build());
    }

    public static ServiceException conflict(String code, String message) {
        return new ServiceException(ServiceError
                .builder()
                .status(CONFLICT.value())
                .message(message)
                .code(code)
                .build());
    }

    public static ServiceException internalError(String code, String message) {
        return new ServiceException(ServiceError
                .builder()
                .status(INTERNAL_SERVER_ERROR.value())
                .message(message)
                .code(code)
                .build());
    }

    public String toString() {
        return "{ServiceException: serviceError= " + this.serviceError + "}";
    }
}
