package com.station3.dabang.application;

import com.station3.dabang.domain.model.Room;
import com.station3.dabang.domain.model.RoomType;
import com.station3.dabang.web.dto.RoomSearchFilter;
import com.station3.dabang.domain.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchRoomService {

    private final RoomRepository roomRepository;

    public List<Room> getRoomsBySearchFilter(RoomType roomType, RoomSearchFilter filter) {
        return roomRepository.findRoomsByFilter(roomType, filter);
    }
}
