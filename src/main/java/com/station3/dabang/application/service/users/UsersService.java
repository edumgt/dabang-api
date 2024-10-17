package com.station3.dabang.application.service.users;

import com.station3.dabang.domain.model.Users;
import com.station3.dabang.exception.DabangException;
import com.station3.dabang.infrastructure.persistence.JpaUsersRepository;
import com.station3.dabang.web.common.ResponseCode;
import com.station3.dabang.web.dto.users.UsersDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final JpaUsersRepository usersRepository;

    public UsersDto addUsers(String email, String encodedPassword) {

        if (usersRepository.findByEmail(email).isPresent()) {
            throw new DabangException(ResponseCode.USERS_ALREADY_EXIST);
        }

        Users user = Users.builder()
                .email(email)
                .password(encodedPassword)
                .build();

        usersRepository.save(user);

        return UsersDto.from(user);
    }

    public UsersDto findByEmail(String email) {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        return UsersDto.from(user);
    }

}
