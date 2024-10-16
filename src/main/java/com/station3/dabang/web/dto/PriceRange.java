package com.station3.dabang.web.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class PriceRange {
    private BigDecimal min;
    private BigDecimal max;
}
