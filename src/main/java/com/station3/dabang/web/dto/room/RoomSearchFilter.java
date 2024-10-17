package com.station3.dabang.web.dto.room;

import com.station3.dabang.domain.model.RoomType;
import com.station3.dabang.domain.model.SellingType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomSearchFilter {
    // 거래 유형 리스트
    private List<SellingType> sellingTypeList;
    // 전세 가격 범위
//    private PriceRange depositRange;
    private int depositMin;
    private int depositMax;
    // 월세 가격 범위
//    private PriceRange priceRange;
    private int priceMin;
    private int priceMax;
}
