package com.station3.dabang.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.station3.dabang.web.common.DabangResponse;
import com.station3.dabang.web.common.ResponseCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    // 인증 완료 후 권한 처리
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException {
        DabangResponse<Object> failResponse = DabangResponse.builder()
                .data(null)
                .result(ResponseCode.FORBIDDEN.result)
                .message(ResponseCode.FORBIDDEN.message)
                .build();

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(failResponse));
    }
}