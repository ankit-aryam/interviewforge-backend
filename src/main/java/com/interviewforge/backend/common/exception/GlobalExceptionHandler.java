package com.interviewforge.backend.common.exception;

import com.interviewforge.backend.common.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(
            ResourceNotFoundException ex
    ) {

        ApiResponse<Void> response =
                ApiResponse.<Void>builder()
                        .success(false)
                        .error(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build();

        log.error("Resource not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneric(
            Exception ex
    ) {
        ex.printStackTrace();
        log.error("Unexpected error", ex);
        ApiResponse<Void> response =
                ApiResponse.<Void>builder()
                        .success(false)
                        .error(ex.getMessage() != null ? ex.getMessage() : "Internal Server Error")
                        .timestamp(LocalDateTime.now())
                        .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}
