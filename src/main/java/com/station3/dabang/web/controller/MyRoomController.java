package com.station3.dabang.web.controller;

import com.station3.dabang.web.dto.room.RoomCreateDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class MyRoomController {

    @GetMapping("/{roomId}")
    @Operation(summary = "내 방 단건 조회")
    public ResponseEntity<String> getMyRoom(@PathVariable long roomId) {
        return ResponseEntity.ok("ok");
    }

    @GetMapping
    @Operation(summary = "내 방 목록 조회")
    public ResponseEntity<String> getMyRoom() {
        return ResponseEntity.ok("ok");
    }

    @PostMapping
    @Operation(summary = "내 방 등록")
    public ResponseEntity<String> createMyRoom(@RequestBody RoomCreateDto roomCreateDto) {
        return ResponseEntity.ok("ok");
    }

    @PutMapping("/{roomId}")
    @Operation(summary = "내 방 수정")
    public ResponseEntity<String> updateMyRoom(
            @PathVariable long roomId,
            @RequestBody RoomCreateDto roomCreateDto) {
        return ResponseEntity.ok("ok");
    }

    @DeleteMapping("/{roomId}")
    @Operation(summary = "내 방 삭제")
    public ResponseEntity<String> deleteMyRoom(@PathVariable long roomId) {
        return ResponseEntity.ok("ok");
    }

}
