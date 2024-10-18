package com.station3.dabang.infrastructure.persistence;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.station3.dabang.domain.model.QRoom;
import com.station3.dabang.domain.model.Room;
import com.station3.dabang.web.dto.room.RoomSearchFilter;
import com.station3.dabang.domain.model.RoomType;
import com.station3.dabang.domain.model.SellingType;
import com.station3.dabang.domain.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaQueryDSLRoomRepository implements JpaCustomRepository {

    private final JPAQueryFactory queryFactory;

    public final static int DEFAULT_MAX_RANGE = 999999;

    @Override
    public Page<Room> findRoomsByFilter(RoomType roomType, RoomSearchFilter filter, Pageable pageable) {
        QRoom room = QRoom.room;

        List<Room> rooms = queryFactory.selectFrom(room)
                .where(
                        roomTypeCondition(roomType), // 방 유형 필터
                        sellingTypeCondition(filter.getSellingTypeList()), // 거래 유형 필터
                        depositRangeCondition(filter.getDepositMin(), filter.getDepositMax()), // 전세 가격 범위 필터
                        priceRangeCondition(filter.getPriceMin(), filter.getPriceMax()) // 월세 가격 범위 필터
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = queryFactory.select(room.count())
                .from(room)
                .where(
                        roomTypeCondition(roomType), // 방 유형 필터
                        sellingTypeCondition(filter.getSellingTypeList()), // 거래 유형 필터
                        depositRangeCondition(filter.getDepositMin(), filter.getDepositMax()), // 전세 가격 범위 필터
                        priceRangeCondition(filter.getPriceMin(), filter.getPriceMax()) // 월세 가격 범위 필터
                )
                .fetchOne();

        return new PageImpl<>(rooms, pageable, count);
    }

    private BooleanExpression roomTypeCondition(RoomType roomType) {
        if (roomType == null) {
            return null;
        }
        return QRoom.room.roomType.eq(roomType);
    }

    private BooleanExpression sellingTypeCondition(List<SellingType> sellingTypeList) {
        if (sellingTypeList == null || sellingTypeList.isEmpty()) {
            return null;
        }

        return QRoom.room.sellingType.in(sellingTypeList);
    }

    private BooleanExpression depositRangeCondition(int depositMin, int depositMax) {
        if (isDefaultRange(depositMin, depositMax)) {
            return null;
        }

        return QRoom.room.deposit.between(depositMin, depositMax);
    }

    private BooleanExpression priceRangeCondition(int priceMin, int priceMax) {
        if (isDefaultRange(priceMin, priceMax)) {
            return null;
        }

        return QRoom.room.price.between(priceMin, priceMax);
    }

    private boolean isDefaultRange(int min, int max) {
        return min == 0 && max == DEFAULT_MAX_RANGE;
    }
}
