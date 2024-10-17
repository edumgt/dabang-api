package com.station3.dabang.infrastructure.persistence;

import com.station3.dabang.domain.model.Users;
import com.station3.dabang.domain.repository.UsersRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUsersRepository extends JpaRepository<Users, Long>, UsersRepository {
}
