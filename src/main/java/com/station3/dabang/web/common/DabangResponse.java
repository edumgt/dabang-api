package com.station3.dabang.web.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DabangResponse<T> {
    private T data;
    private int result;
    private String message;
}
