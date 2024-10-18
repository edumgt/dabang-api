package com.station3.dabang.infrastructure.persistence;

import com.station3.dabang.domain.model.Room;
import com.station3.dabang.domain.model.RoomType;
import com.station3.dabang.domain.repository.RoomRepository;
import com.station3.dabang.web.dto.room.RoomSearchFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JpaCustomRepository {
    Page<Room> findRoomsByFilter(RoomType roomType, RoomSearchFilter filter, Pageable pageable);
}
