package com.station3.dabang.web.controller;

import com.station3.dabang.application.service.room.SearchRoomService;
import com.station3.dabang.domain.model.Room;
import com.station3.dabang.domain.model.RoomType;
import com.station3.dabang.web.common.DabangResponse;
import com.station3.dabang.web.common.PageDto;
import com.station3.dabang.web.common.ResponseCode;
import com.station3.dabang.web.dto.room.RoomSearchFilter;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/search/rooms")
@RequiredArgsConstructor
public class RoomSearchController {
    private final SearchRoomService searchRoomService;

    @GetMapping("/type/{roomType}")
    @Operation(summary = "전체 방 목록 검색")
    public ResponseEntity<DabangResponse<PageDto<Room>>> getRoomsBySearchFilter(
            @PathVariable String roomType,
            @ModelAttribute RoomSearchFilter filter,
            Pageable pageable
    ) {
        RoomType type = getRoomType(roomType);
        PageDto<Room> rooms = searchRoomService.getRoomsBySearchFilter(type, filter, pageable);
        return ResponseEntity.ok(
                DabangResponse.<PageDto<Room>>builder()
                        .data(rooms)
                        .result(ResponseCode.SUCCESS.result)
                        .message(ResponseCode.SUCCESS.message)
                        .build()
        );
    }

    private RoomType getRoomType(String roomType) {
        try {
            if ("ALL".equalsIgnoreCase(roomType)) {
                return null;
            }
            return RoomType.valueOf(roomType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid room type: " + roomType);
        }
    }

}
