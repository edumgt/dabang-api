package com.station3.dabang.domain.repository;

import com.station3.dabang.domain.model.Room;
import com.station3.dabang.domain.model.RoomType;
import com.station3.dabang.web.dto.RoomSearchFilter;

import java.util.List;

public interface RoomRepository {
    List<Room> findRoomsByFilter(RoomType roomType, RoomSearchFilter filter);
}
