package com.sparta.bookflex.domain.shipment.entity;

import com.sparta.bookflex.common.utill.Timestamped;
import com.sparta.bookflex.domain.orderbook.entity.OrderBook;
import com.sparta.bookflex.domain.orderbook.entity.OrderItem;
import com.sparta.bookflex.domain.sale.Enum.SaleState;
import com.sparta.bookflex.domain.sale.entity.Sale;
import com.sparta.bookflex.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Shipment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipment_id")
    private Long id;

    @Column(name = "tracking_number",nullable = false)
    private String trackingNumber;

    @Column(name = "carrier",nullable = false)
    private String carrier;

    @Column(name = "status",nullable = false)
    private SaleState status;


    @Column(name = "shipped_at")
    private LocalDateTime shippedAt;

    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;

    @OneToMany(mappedBy = "shipment")
    private List<OrderItem> orderItemList;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @Builder
    public Shipment(String trackingNumber, String carrier, SaleState status, LocalDateTime shippedAt, LocalDateTime deliveredAt, List<OrderItem> orderItemList,User user) {
        this.trackingNumber = trackingNumber;
        this.carrier = carrier;
        this.status = status;
        this.shippedAt = shippedAt;
        this.deliveredAt = deliveredAt;
        this.orderItemList = orderItemList;
        this.user = user;
    }


}
