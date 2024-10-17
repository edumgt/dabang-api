package com.station3.dabang.domain.repository;

import com.station3.dabang.domain.model.Users;

import java.util.Optional;

public interface UsersRepository {
    Optional<Users> findByEmail(String email);
}
