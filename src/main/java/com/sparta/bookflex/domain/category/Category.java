package com.sparta.bookflex.domain.category;

import java.util.*;
import java.util.stream.Collectors;

/*
국내도서
소설
인문
경제
역사
종교
예술
과학
자기계발
여행
요리
가정/육아
잡지
사전
외국어
참고서

    시/에세이

    건강
    취미/실용/스포츠

    기술/공학
    컴퓨터/IT


외국도서
    영미도서
    일본도서
    중국도서
    프랑스도서
    독일도서
    스페인도서

    러시아도서
    북유럽도서
    그외유럽도서
    기타나라도서
*/

public enum Category {

    ROOT("카테고리", null),
        DOMESTIC("국내도서",ROOT),
            FICTION("소설",DOMESTIC),
            SOCIAL_SCIENCE("인문",DOMESTIC),
            BUSINESS("경제",DOMESTIC),
            HISTORY("역사",DOMESTIC),
            RELIGION("종교",DOMESTIC),
            ART("예술",DOMESTIC),
            SCIENCE("과학",DOMESTIC),
            SELF_HELP("자기계발",DOMESTIC),
            TRAVEL("여행",DOMESTIC),
            COOKING("요리",DOMESTIC),
            PARENTING("육아",DOMESTIC),
            MAGAZINE("잡지",DOMESTIC),
            DICTIONARY("사전",DOMESTIC),
            FOREIGN_LANGUAGES("외국어",DOMESTIC),
            EXAMS("참고서",DOMESTIC),
            //    ("",DOMESTIC),

        INTERNATIONAL("외국도서",ROOT),
            ENGLISH("영미도서",INTERNATIONAL),
            JAPAN("일본도서",INTERNATIONAL),
            CHINA("중국도서",INTERNATIONAL),
            FRANCE("프랑스도서",INTERNATIONAL),
            GERMANY("독일도서",INTERNATIONAL) ,
            //    ("",INTERNATIONAL),
            SPAIN("스페인도서",INTERNATIONAL)
    ;

    // 카테고리 이름
    private final String categoryName;

    // 부모 카테고리
    private final Category mainCategory;

    // 자식카테고리
    private final List<Category> subCategories;

    Category(String categoryName, Category mainCategory) {
        this.subCategories = new ArrayList<>();
        this.categoryName = categoryName;
        this.mainCategory = mainCategory;
        if(Objects.nonNull(mainCategory)) {
            mainCategory.subCategories.add(this);
        }
    }

    public String getCategoryName() {
        return categoryName;
    }

    // 부모카테고리 Getter
    public Optional<Category> getMainCategory() {
        return Optional.ofNullable(mainCategory);
    }

    // 자식카테고리 Getter
    public List<Category> getSubCategories() {
        return Collections.unmodifiableList(subCategories);
    }

    // 마지막 카테고리(상품추가 가능)인지 반환
    public boolean isLeafCategory() {
        return subCategories.isEmpty();
    }

    // 마지막 카테고리(상품추가 가능)들 반환
    public List<Category> getLeafCategories() {
        return Arrays.stream(Category.values())
                .filter(category -> category.isLeafCategoryOf(this))
                .collect(Collectors.toList());
    }

    private boolean isLeafCategoryOf(Category category) {
        return this.isLeafCategory() && category.contains(this);
    }

    private boolean contains(Category category) {
        if(this.equals(category)) return true;

        return Objects.nonNull(category.mainCategory) &&
                this.contains(category.mainCategory);
    }
}