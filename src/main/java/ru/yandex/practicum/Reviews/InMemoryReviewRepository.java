package ru.yandex.practicum.Reviews;

import java.util.ArrayList;
import java.util.List;

public class InMemoryReviewRepository implements ReviewRepository {
    private final List<Review> reviews = new ArrayList<>();
    private Long nextId = 1L;

    @Override
    public Review save(Review review) {
        if (review.getId() == null) {
            review.setId(nextId++);
        }
        reviews.removeIf(r -> r.getId().equals(review.getId()));
        reviews.add(review);
        return review;
    }
}