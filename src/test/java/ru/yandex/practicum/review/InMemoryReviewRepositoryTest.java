package ru.yandex.practicum.review;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.Reviews.InMemoryReviewRepository;
import ru.yandex.practicum.Reviews.Review;
import ru.yandex.practicum.item.Item;
import ru.yandex.practicum.user.User;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryReviewRepositoryTest {

    private InMemoryReviewRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryReviewRepository();
    }

    @Test
    void saveReview_shouldAssignIdAndStore() {
        Review review = new Review();
        review.setText("Great item!");
        review.setAuthor(new User());
        review.setItem(new Item());

        Review savedReview = repository.save(review);

        assertNotNull(savedReview.getId());
        assertEquals("Great item!", savedReview.getText());
    }
}