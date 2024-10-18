package com.station3.dabang.domain.repository;

import com.station3.dabang.domain.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoomRepository {
    Room findRoomByIdAndUserId(Long roomId, Long userId);
    Page<Room> findRoomsByUserId(Long userId, Pageable pageable);
}