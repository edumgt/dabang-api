package com.station3.dabang.application.service.room;

import com.station3.dabang.domain.model.Room;
import com.station3.dabang.domain.model.RoomType;
import com.station3.dabang.domain.repository.RoomRepository;
import com.station3.dabang.web.common.PageDto;
import com.station3.dabang.web.dto.room.RoomSearchFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SearchRoomService {

    private final RoomRepository roomRepository;

    public PageDto<Room> getRoomsBySearchFilter(RoomType roomType, RoomSearchFilter filter, Pageable pageable) {
        Page<Room> rooms = roomRepository.findRoomsByFilter(roomType, filter, pageable);
        return PageDto.from(rooms);
    }
}
