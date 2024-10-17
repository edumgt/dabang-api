package com.station3.dabang.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.station3.dabang.web.common.DabangResponse;
import com.station3.dabang.web.common.ResponseCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    // 인증
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        DabangResponse<Object> failResponse = DabangResponse.builder()
                .data(null)
                .result(ResponseCode.UNAUTHORIZED.result)
                .message(ResponseCode.UNAUTHORIZED.message)
                .build();

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(failResponse));
    }
}
