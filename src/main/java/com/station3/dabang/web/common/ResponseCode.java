package com.station3.dabang.web.common;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum ResponseCode {
    SUCCESS(200, "성공", HttpStatus.OK),
    BAD_REQUEST(400, "잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(401, "인증되지 않았습니다.", HttpStatus.UNAUTHORIZED),
    FORBIDDEN(403, "권한이 없습니다.", HttpStatus.FORBIDDEN),
    INTERNAL_SERVER_ERROR(500, "API 요청 처리 중 에러 발생", HttpStatus.INTERNAL_SERVER_ERROR),
    // users
    USERS_ALREADY_EXIST(500, "해당 유저 이메일은 이미 존재합니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    // room
    ROOM_NOT_FOUND(404, "해당 방이 존재하지 않습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    public final int result;
    public final String message;
    public final HttpStatus status;
}
