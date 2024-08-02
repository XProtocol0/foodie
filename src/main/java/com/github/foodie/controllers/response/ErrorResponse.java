package com.github.foodie.controllers.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.MDC;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ErrorResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String errorCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String errorMsg;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime timestamp;
    String correlationId;
    int status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<String> reqValidationErrors;

    public ErrorResponse(String errorCode, String errorMsg) {
        super();
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.correlationId = MDC.get("correlationId");
    }

    public ErrorResponse() {
        super();
        this.correlationId = MDC.get("correlationId");
    }
}
