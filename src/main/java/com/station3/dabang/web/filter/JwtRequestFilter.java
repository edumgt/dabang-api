package com.station3.dabang.web.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.station3.dabang.application.service.auth.CustomUserDetailsService;
import com.station3.dabang.utils.JwtUtil;
import com.station3.dabang.web.common.DabangResponse;
import com.station3.dabang.web.common.ResponseCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        try {

            if (checkOpenPath(request.getServletPath())) {
                chain.doFilter(request, response);
                return;
            }

            final String authorizationHeader = request.getHeader("Authorization");

            String username = null;
            String jwt = null;

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7);
                username = jwtUtil.extractUsername(jwt);
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                    Long userId = jwtUtil.extractUserId(jwt);  // 토큰에서 userId 추출

                    // SecurityContext에 사용자 정보 저장
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userId, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }

            chain.doFilter(request, response);

        } catch (MalformedJwtException e) {
            sendErrorResponse(response, "손상된 토큰입니다.");
        } catch (ExpiredJwtException e) {
            sendErrorResponse(response, "만료된 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            sendErrorResponse(response, "지원하지 않는 토큰입니다.");
        } catch (SignatureException e) {
            sendErrorResponse(response, "시그니처 검증에 실패한 토큰입니다.");
        }

    }

    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        DabangResponse<Object> failResponse = DabangResponse.builder()
                .data(null)
                .result(ResponseCode.UNAUTHORIZED.result)
                .message(message)
                .build();

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(failResponse));
    }

    private final static List<String> openPaths = Arrays.asList(
            "/api/v1/authenticate",
            "/api/v1/register",
            "/api/v1/search/rooms/**"
    );

    private boolean checkOpenPath(String requestPath) {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return openPaths.stream().anyMatch(path -> pathMatcher.match(path, requestPath));
    }
}
