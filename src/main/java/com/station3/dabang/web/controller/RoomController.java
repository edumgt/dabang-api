package com.station3.dabang.web.controller;

import com.station3.dabang.application.SearchRoomService;
import com.station3.dabang.domain.model.Room;
import com.station3.dabang.domain.model.RoomType;
import com.station3.dabang.web.dto.RoomSearchFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final SearchRoomService searchRoomService;

    @GetMapping("/type/{roomType}")
    public ResponseEntity<List<Room>> getRoomsBySearchFilter(
            @PathVariable String roomType,
            @ModelAttribute RoomSearchFilter filter
    ) {
        RoomType type = getRoomType(roomType);
        List<Room> rooms = searchRoomService.getRoomsBySearchFilter(type, filter);
        return ResponseEntity.ok(rooms);
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
