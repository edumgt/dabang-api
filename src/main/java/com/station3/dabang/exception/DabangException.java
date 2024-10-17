package com.station3.dabang.exception;

import com.station3.dabang.web.common.ResponseCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DabangException extends RuntimeException  {
    protected int result;
    protected String message;
    protected HttpStatus status;

    public DabangException(ResponseCode responseCode) {
        super(responseCode.message);

        this.result = responseCode.result;
        this.message = responseCode.message;
        this.status = responseCode.status;
    }

    public DabangException(ResponseCode responseCode, String customMessage) {
        super(responseCode.message);
        this.message = customMessage;
    }
}
