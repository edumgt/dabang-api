package com.station3.dabang.web.controller;


import com.station3.dabang.application.service.auth.CustomUserDetailsService;
import com.station3.dabang.application.service.users.UsersService;
import com.station3.dabang.utils.JwtUtil;
import com.station3.dabang.web.common.DabangResponse;
import com.station3.dabang.web.common.ResponseCode;
import com.station3.dabang.web.dto.auth.AuthRequest;
import com.station3.dabang.web.dto.auth.AuthResponse;
import com.station3.dabang.web.dto.users.UsersDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final UsersService usersService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    @Operation(summary = "회원 가입")
    public ResponseEntity<DabangResponse<UsersDto>> registerUser(@RequestBody AuthRequest authRequest) {
        UsersDto userDto = usersService.addUsers(authRequest.getEmail(), passwordEncoder.encode(authRequest.getPassword()));
        return ResponseEntity.ok(
                DabangResponse.<UsersDto>builder()
                        .data(userDto)
                        .result(ResponseCode.SUCCESS.result)
                        .message(ResponseCode.SUCCESS.message)
                        .build()
        );
    }

    @PostMapping("/authenticate")
    @Operation(summary = "로그인 인증")
    public ResponseEntity<DabangResponse<AuthResponse>> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());

        // 이메일을 기반으로 DB에서 userId를 가져옴
        UsersDto user = usersService.findByEmail(authRequest.getEmail());
        Long userId = user.getUserId();

        final String jwt = jwtUtil.generateToken(userId, userDetails.getUsername());

        return ResponseEntity.ok(
                DabangResponse.<AuthResponse>builder()
                        .data(new AuthResponse(jwt))
                        .result(ResponseCode.SUCCESS.result)
                        .message(ResponseCode.SUCCESS.message)
                        .build()
        );
    }

}
