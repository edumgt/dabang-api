package com.station3.dabang.exception.advice;

import com.station3.dabang.exception.DabangException;
import com.station3.dabang.web.common.DabangResponse;
import com.station3.dabang.web.common.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Throwable.class)
    protected ResponseEntity<DabangResponse<Object>> handleThrowable(Throwable throwable) {
        DabangResponse<Object> response = DabangResponse.builder()
                .data(null)
                .result(ResponseCode.INTERNAL_SERVER_ERROR.result)
                .message(ResponseCode.INTERNAL_SERVER_ERROR.message)
                .build();

        // 예측하지 못한 에러 로그 확인 용. 응답에는 노출되지 않도록 처리.
        log.error("Server error msg : {}", throwable.getMessage(), throwable);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);

    }

    @ExceptionHandler(DabangException.class)
    protected ResponseEntity<DabangResponse<Object>> handleDabangException(DabangException ex) {
        DabangResponse<Object> response = DabangResponse.builder()
                .data(null)
                .result(ex.getResult())
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);

    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<DabangResponse<Object>> handleBadCredentialsException(BadCredentialsException ex) {
        DabangResponse<Object> response = DabangResponse.builder()
                .data(null)
                .result(ResponseCode.UNAUTHORIZED.result)
                .message(ResponseCode.UNAUTHORIZED.message)
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }

}
