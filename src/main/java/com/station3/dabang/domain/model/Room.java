package com.station3.dabang.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "room")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    @Enumerated(EnumType.STRING)
    private RoomType roomType;
    @Enumerated(EnumType.STRING)
    private SellingType sellingType;
    private int deposit; // 보증금
    private int price; // 월세
    private String description;

    public void updateRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public void updateSellingType(SellingType sellingType) {
        this.sellingType = sellingType;
    }

    public void updateDeposit(int deposit) {
        this.deposit = deposit;
    }

    public void updatePrice(int price) {
        this.price = price;
    }

    public void updateDescription(String description) {
        this.description = description;
    }
}
