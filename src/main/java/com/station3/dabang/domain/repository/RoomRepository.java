package com.station3.dabang.domain.repository;

import com.station3.dabang.domain.model.Room;
import com.station3.dabang.domain.model.RoomType;
import com.station3.dabang.web.dto.room.RoomSearchFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoomRepository {
    Page<Room> findRoomsByFilter(RoomType roomType, RoomSearchFilter filter, Pageable pageable);
}
