package com.station3.dabang.web.dto.users;

import com.station3.dabang.domain.model.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto {
    private Long userId;
    private String email;

    public static UsersDto from(Users user) {
        return UsersDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .build();
    }
}
