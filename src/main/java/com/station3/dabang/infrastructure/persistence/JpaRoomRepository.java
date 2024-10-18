package com.station3.dabang.infrastructure.persistence;

import com.station3.dabang.domain.model.Room;
import com.station3.dabang.domain.repository.RoomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaRoomRepository extends JpaRepository<Room, Long>, RoomRepository {
}
