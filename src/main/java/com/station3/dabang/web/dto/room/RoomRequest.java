package com.station3.dabang.web.dto.room;

import com.station3.dabang.domain.model.RoomType;
import com.station3.dabang.domain.model.SellingType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoomRequest {
    @Schema(description = "방 유형", example = "ONE_ROOM")
    private RoomType roomType;
    @Schema(description = "거래 유형", example = "MONTHLY_RENT")
    private SellingType sellingType;
    @Schema(description = "보증금 (만원 단위)", example = "1000")
    private int deposit;
    @Schema(description = "월세 (만원 단위)", example = "50")
    private int price;
    @Schema(description = "방 설명", example = "깨끗한 방입니다~")
    private String description;
}
