package com.station3.dabang.web.controller;

import com.station3.dabang.application.service.room.RoomService;
import com.station3.dabang.domain.model.Room;
import com.station3.dabang.utils.SecurityUtil;
import com.station3.dabang.web.common.DabangResponse;
import com.station3.dabang.web.common.PageDto;
import com.station3.dabang.web.common.ResponseCode;
import com.station3.dabang.web.dto.room.RoomRequest;
import com.station3.dabang.web.dto.room.RoomResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.station3.dabang.utils.SecurityUtil.getCurrentUserId;

@RestController
@RequestMapping("/api/v1/my/rooms")
@RequiredArgsConstructor
public class MyRoomController {

    private final RoomService roomService;

    @GetMapping("/{roomId}")
    @Operation(summary = "내 방 단건 조회")
    public ResponseEntity<DabangResponse<RoomResponse>> getMyRoom(@PathVariable long roomId) {
        RoomResponse room = roomService.getMyRoom(roomId, SecurityUtil.getCurrentUserId());
        return ResponseEntity.ok(
                DabangResponse.<RoomResponse>builder()
                        .data(room)
                        .result(ResponseCode.SUCCESS.result)
                        .message(ResponseCode.SUCCESS.message)
                        .build()
        );
    }

    @GetMapping
    @Operation(summary = "내 방 목록 조회")
    public ResponseEntity<DabangResponse<PageDto<RoomResponse>>> getMyRoom(Pageable pageable) {
        PageDto<RoomResponse> rooms = roomService.getMyRooms(SecurityUtil.getCurrentUserId(), pageable);
        return ResponseEntity.ok(
                DabangResponse.<PageDto<RoomResponse>>builder()
                        .data(rooms)
                        .result(ResponseCode.SUCCESS.result)
                        .message(ResponseCode.SUCCESS.message)
                        .build()
        );
    }

    @PostMapping
    @Operation(summary = "내 방 등록")
    public ResponseEntity<DabangResponse<RoomResponse>> createMyRoom(@RequestBody RoomRequest roomRequest) {
        RoomResponse room = roomService.createMyRoom(getCurrentUserId(), roomRequest);
        return ResponseEntity.ok(
                DabangResponse.<RoomResponse>builder()
                        .data(room)
                        .result(ResponseCode.SUCCESS.result)
                        .message(ResponseCode.SUCCESS.message)
                        .build()
        );
    }

    @PutMapping("/{roomId}")
    @Operation(summary = "내 방 수정")
    public ResponseEntity<DabangResponse<RoomResponse>> updateMyRoom(
            @PathVariable long roomId,
            @RequestBody RoomRequest roomRequest) {
        RoomResponse room = roomService.updateMyRoom(roomId, getCurrentUserId(), roomRequest);
        return ResponseEntity.ok(
                DabangResponse.<RoomResponse>builder()
                        .data(room)
                        .result(ResponseCode.SUCCESS.result)
                        .message(ResponseCode.SUCCESS.message)
                        .build()
        );
    }

    @DeleteMapping("/{roomId}")
    @Operation(summary = "내 방 삭제")
    public ResponseEntity<DabangResponse<Long>> deleteMyRoom(@PathVariable long roomId) {
        roomService.deleteRoom(roomId, getCurrentUserId());
        return ResponseEntity.ok(
                DabangResponse.<Long>builder()
                        .data(roomId)
                        .result(ResponseCode.SUCCESS.result)
                        .message(ResponseCode.SUCCESS.message)
                        .build()
        );
    }

}
