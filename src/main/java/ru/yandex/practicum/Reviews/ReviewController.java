package ru.yandex.practicum.Reviews;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ReviewDto> createReview(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                  @RequestBody ReviewDto reviewDto) {
        Review review = reviewService.createReview(userId, reviewDto);
        return new ResponseEntity<>(ReviewMapper.toReviewDto(review), HttpStatus.CREATED);
    }
}
