package com.sparta.bookflex.domain.book.controller;

import com.sparta.bookflex.common.dto.CommonDto;
import com.sparta.bookflex.domain.book.dto.BookRequestDto;
import com.sparta.bookflex.domain.book.dto.BookResponseDto;
import com.sparta.bookflex.domain.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class BookController {

    private final BookService bookService;

    @PostMapping
    public CommonDto<BookResponseDto> registerProduct(@RequestPart(value = "request") BookRequestDto bookRequestDto,
                                                      @RequestPart(value = "multipartFile") MultipartFile multipartFile) throws IOException {

        BookResponseDto bookResponseDto = bookService.registerProduct(bookRequestDto, multipartFile);

        return new CommonDto<>(HttpStatus.CREATED.value(), "상품 등록에 성공하였습니다.", bookResponseDto);
    }

    @GetMapping("/{productId}")
    public CommonDto<BookResponseDto> getBook(@RequestBody BookRequestDto bookRequestDto) {

        BookResponseDto bookResponseDto = bookService.getBook(bookRequestDto);

        return new CommonDto<>(HttpStatus.OK.value(), "상품 조회에 성공하였습니다.", bookResponseDto);
    }

    //페이징 적용 전
    @GetMapping
    public CommonDto<List<BookResponseDto>> getBookList() {

        List<BookResponseDto> bookResponseDtoList = bookService.getBookList();

        if (bookResponseDtoList == null) {
            return new CommonDto<>(HttpStatus.OK.value(), "등록된 상품이 존재하지 않습니다.", bookResponseDtoList);
        }

        return new CommonDto<>(HttpStatus.OK.value(), "상품 전체 조회에 성공하였습니다.", bookResponseDtoList);
    }

    @PutMapping("/{productId}")
    public CommonDto<BookResponseDto> modifyBookInfo(@PathVariable(value = "productId") Long bookId,
                                                     @RequestPart(value = "request") BookRequestDto bookRequestDto,
                                                     @RequestPart(value = "multipartFile") MultipartFile multipartFile) throws IOException {

        BookResponseDto bookResponseDto = bookService.modifyBookInfo(bookId, bookRequestDto, multipartFile);

        return new CommonDto<>(HttpStatus.OK.value(), "상품 정보 수정에 성공하였습니다.", bookResponseDto);
    }

    @DeleteMapping("/{productId}")
    public CommonDto<String> DeleteBook(@PathVariable(value = "productId") Long bookId) {

        String bookName = bookService.deleteBook(bookId);

        return new CommonDto<>(HttpStatus.OK.value(), "상품 삭제에 성공하였습니다.", bookName + " 을 상품 목록에서 삭제하였습니다");
    }
}