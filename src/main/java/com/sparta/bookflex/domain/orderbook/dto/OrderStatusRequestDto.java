package com.sparta.bookflex.domain.orderbook.dto;

import com.sparta.bookflex.domain.sale.Enum.SaleState;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderStatusRequestDto {
    private SaleState status;
}