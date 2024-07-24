package com.sparta.bookflex.domain.basket.entity;

import com.sparta.bookflex.common.utill.Timestamped;
import com.sparta.bookflex.domain.basket.entity.Basket;
import com.sparta.bookflex.domain.book.entity.Book;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@Table(name = "book_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BasketItem extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name="book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name="basket_id", nullable = false)
    private Basket basket;
}
