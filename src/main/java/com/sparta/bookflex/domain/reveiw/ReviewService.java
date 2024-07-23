package com.sparta.bookflex.domain.reveiw;

import com.sparta.bookflex.common.exception.BusinessException;
import com.sparta.bookflex.common.exception.ErrorCode;
import com.sparta.bookflex.domain.book.entity.Book;
import com.sparta.bookflex.domain.book.service.BookService;
import com.sparta.bookflex.domain.reveiw.entity.Review;
import com.sparta.bookflex.domain.reveiw.repository.ReviewRepository;
import com.sparta.bookflex.domain.sale.entity.Sale;
import com.sparta.bookflex.domain.sale.repository.SaleRepository;
import com.sparta.bookflex.domain.user.entity.User;
import com.sparta.bookflex.domain.user.enums.UserRole;
import com.sparta.bookflex.domain.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final AuthService authService;
    private final BookService bookService;
//    private final SaleService saleService;
    private final SaleRepository saleRepository;

    /*
    레포지토리 접근 부분 추후 수정 필요
     */
    @Transactional
    public ReviewResponseDto createReview(User user, Long saleId, ReviewRequestDto reviewRequestDto) {
        User selectedUser = getUser(user.getUsername());
//        Sale selectedSale = saleService.getSale(saleId);
        Sale selectedSale = saleRepository.findById(saleId).orElseThrow(()->new IllegalArgumentException());
        Book selectedBook = bookService.getBookByBookId(selectedSale.getBook().getId());

        Review createdReview = reviewRequestDto.toEntity(selectedUser, selectedBook);

        createdReview = reviewRepository.save(createdReview);

        selectedUser.getReviewList().add(createdReview);

        selectedBook.getReviewList().add(createdReview);

        return createdReview.toResponseDto();

    }

    public ReviewResponseDto getReview(Long reviewId) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BusinessException(ErrorCode.REVIEW_NOT_FOUND));

        return review.toResponseDto();
    }

    @Transactional
    public ReviewResponseDto modifyReview(User user, Long reviewId, ReviewRequestDto reviewRequestDto) {

        Review review = getReviewById(reviewId);

        if (user.getId() != (review.getUser().getId())) {
            throw new BusinessException(ErrorCode.USER_NOT_WRITER);
        }

        review.update(reviewRequestDto);

        return review.toResponseDto();
    }

    public String deleteReview(User user, Long reviewId) {

        Review review = getReviewById(reviewId);

        String reviewTitle = review.getTitle();

        if (user.getId() != (review.getUser().getId()) && !user.getAuth().equals(UserRole.ADMIN)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED_ACTION);
        }

        reviewRepository.delete(review);

        return reviewTitle;
    }

    private User getUser(String username) {

        return authService.findByUserName(username);
    }

    private Review getReviewById(Long reviewId) {

        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BusinessException(ErrorCode.REVIEW_NOT_FOUND));
    }

    public List<ReviewResponseDto> getAllReviews() {

        return reviewRepository.findAll().stream().map(Review::toResponseDto).toList();
    }
}
