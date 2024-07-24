package com.sparta.bookflex.domain.category;

import com.sparta.bookflex.common.aop.Envelop;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryEnumController {
    private final CategoryEnumService categoryEnumService;

    /*모든 카테고리 목록 조회
    GET /categories

    응답DTO
    statusCode	200
    message	“모든 카테고리 조회에 성공하였습니다.”
    data	{
            categoryName	카테고리 이름
            },
            {
            categoryName	카테고리 이름
            }, …
    * */
    @GetMapping("/enum")
    @Envelop("모든 카테고리 조회에 성공하였습니다.")
    public ResponseEntity<List<CategoryEnumResponseDto>> getAllCategories() {

        List<CategoryEnumResponseDto> responseList = categoryEnumService.getAllCategories();
        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }

    @GetMapping("/main")
    @Envelop("메인 카테고리 조회에 성공하였습니다.")
    public ResponseEntity<List<CategoryNameResponseDto>> getAllMainCategories() {

        List<CategoryNameResponseDto> responseList = categoryEnumService.getAllMainCategories();
        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }

    /*서브 카테고리 목록 조회
    GET /categories/sub

    응답DTO
    statusCode	200
    message	“서브 카테고리 조회에 성공하였습니다”
    data	{
            categoryName	카테고리 이름
            },
            {
            categoryName	카테고리 이름
            }, …	*/
    @GetMapping("/sub")
    @Envelop("서브 카테고리 목록 조회에 성공하였습니다.")
    public ResponseEntity<List<CategoryNameResponseDto>> getAllSubCategories(@RequestParam(value = "mainCategory", defaultValue = "카테고리") String mainCategory) {

        List<CategoryNameResponseDto> responseList = categoryEnumService.getAllSubCategories(mainCategory);
        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }
}
