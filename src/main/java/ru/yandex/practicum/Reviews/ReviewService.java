package ru.yandex.practicum.Reviews;

public interface ReviewService {
    Review createReview(Long userId, ReviewDto reviewDto);
}
