package com.station3.dabang.web.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaginationDto {
    @Schema(description = "전체 페이지 수")
    private long totalPages;
    @Schema(description = "전체 데이터 갯수")
    private long totalElements;
    @Schema(description = "한 페이지 내 최대 데이터 갯수", defaultValue = "20")
    private long size;
    @Schema(description = "현재 페이지 번호 (min:0)")
    private long number;
    @Schema(description = "현재 페이지 내 데이터 갯수")
    private long numberOfElements;
}
