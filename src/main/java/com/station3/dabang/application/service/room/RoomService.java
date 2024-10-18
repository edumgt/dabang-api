package com.station3.dabang.application.service.room;

import com.station3.dabang.domain.model.Room;
import com.station3.dabang.domain.repository.RoomRepository;
import com.station3.dabang.exception.DabangException;
import com.station3.dabang.infrastructure.persistence.JpaRoomRepository;
import com.station3.dabang.web.common.PageDto;
import com.station3.dabang.web.common.ResponseCode;
import com.station3.dabang.web.dto.room.RoomRequest;
import com.station3.dabang.web.dto.room.RoomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final JpaRoomRepository jpaRoomRepository;

    public RoomResponse createMyRoom(Long userId, RoomRequest roomRequest) {
        Room room = Room.builder()
                .roomType(roomRequest.getRoomType())
                .sellingType(roomRequest.getSellingType())
                .deposit(roomRequest.getDeposit())
                .price(roomRequest.getPrice())
                .description(roomRequest.getDescription())
                .userId(userId)
                .build();
        jpaRoomRepository.save(room);

        return new RoomResponse(room);
    }

    public RoomResponse updateMyRoom(Long roomId, Long userId, RoomRequest roomRequest) {

        Room room = roomRepository.findRoomByIdAndUserId(roomId, userId);
        if (room == null) {
            throw new DabangException(ResponseCode.ROOM_NOT_FOUND);
        }

        room.updateRoomType(roomRequest.getRoomType());
        room.updateSellingType(roomRequest.getSellingType());
        room.updateDeposit(roomRequest.getDeposit());
        room.updatePrice(roomRequest.getPrice());
        room.updateDescription(roomRequest.getDescription());

        jpaRoomRepository.save(room);

        return new RoomResponse(room);
    }

    public void deleteRoom(Long roomId, Long userId) {
        Room room = roomRepository.findRoomByIdAndUserId(roomId, userId);
        if (room == null) {
            throw new DabangException(ResponseCode.ROOM_NOT_FOUND);
        }

        jpaRoomRepository.delete(room);
    }

    public RoomResponse getMyRoom(Long roomId, Long userId) {
        Room room = roomRepository.findRoomByIdAndUserId(roomId, userId);
        if (room == null) {
            throw new DabangException(ResponseCode.ROOM_NOT_FOUND);
        }

        return new RoomResponse(room);
    }

    public PageDto<RoomResponse> getMyRooms(Long userId, Pageable pageable) {
        Page<Room> rooms = roomRepository.findRoomsByUserId(userId, pageable);
        Page<RoomResponse> roomsResponse = rooms.map(RoomResponse::new);

        return PageDto.from(roomsResponse);
    }

}
