package com.github.foodie.exceptions;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceError {
    private final int status;
    private final String message;
    private final String code;
    private Object reqValidationErr;

    private ServiceError(int status, String message, String code, Object reqValidationErr) {
        this.status = status;
        this.message = message;
        this.code = code;
        this.reqValidationErr = reqValidationErr;
    }

    public String toString() {
        return "{ServiceError: status= " + this.status + ", message= '" + this.message + "'}";
    }
}
