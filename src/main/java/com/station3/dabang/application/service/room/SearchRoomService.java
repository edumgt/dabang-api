package com.station3.dabang.application.service.room;

import com.station3.dabang.domain.model.Room;
import com.station3.dabang.domain.model.RoomType;
import com.station3.dabang.domain.repository.RoomRepository;
import com.station3.dabang.infrastructure.persistence.JpaCustomRepository;
import com.station3.dabang.infrastructure.persistence.JpaRoomRepository;
import com.station3.dabang.web.common.PageDto;
import com.station3.dabang.web.dto.room.RoomResponse;
import com.station3.dabang.web.dto.room.RoomSearchFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SearchRoomService {

    private final JpaCustomRepository roomRepository;

    public PageDto<RoomResponse> getRoomsBySearchFilter(RoomType roomType, RoomSearchFilter filter, Pageable pageable) {
        Page<Room> rooms = roomRepository.findRoomsByFilter(roomType, filter, pageable);
        Page<RoomResponse> roomsResponse = rooms.map(RoomResponse::new);
        return PageDto.from(roomsResponse);
    }
}
