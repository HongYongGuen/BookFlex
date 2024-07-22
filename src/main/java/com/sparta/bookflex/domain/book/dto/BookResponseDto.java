package com.sparta.bookflex.domain.book.dto;

import com.sparta.bookflex.domain.category.entity.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class BookResponseDto {
    private Long bookId;
    private String bookName;
    private String author;
    private int price;
    private int stock;
    private String bookDescription;
    private String status;
    private String category;
    private String photoImagePath;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Builder
    public BookResponseDto(Long bookId,
                           String bookName,
                           String author,
                           int price,
                           int stock,
                           String bookDescription,
                           String status,
                           Category category,
                           String photoImagePath,
                           LocalDateTime createdAt,
                           LocalDateTime modifiedAt) {

        this.bookId = bookId;
        this.bookName = bookName;
        this.author = author;
        this.price = price;
        this.stock = stock;
        this.bookDescription = bookDescription;
        this.status = status;
        this.category=category.getCategoryName();
        this.photoImagePath = photoImagePath;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
