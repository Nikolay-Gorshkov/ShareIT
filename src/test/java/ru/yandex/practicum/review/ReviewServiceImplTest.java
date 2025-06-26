package ru.yandex.practicum.review;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.yandex.practicum.Reviews.Review;
import ru.yandex.practicum.Reviews.ReviewDto;
import ru.yandex.practicum.Reviews.ReviewRepository;
import ru.yandex.practicum.Reviews.ReviewServiceImpl;
import ru.yandex.practicum.item.Item;
import ru.yandex.practicum.item.ItemService;
import ru.yandex.practicum.user.User;
import ru.yandex.practicum.user.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserService userService;

    @Mock
    private ItemService itemService;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createReview_shouldSaveReview() {
        User user = new User();
        user.setId(1L);

        Item item = new Item();
        item.setId(1L);

        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setText("Great item!");
        reviewDto.setItemId(1L);

        Review review = new Review();
        review.setId(1L);
        review.setText("Great item!");

        when(userService.getUserById(1L)).thenReturn(user);
        when(itemService.getItemById(1L)).thenReturn(item);
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        Review savedReview = reviewService.createReview(1L, reviewDto);

        assertNotNull(savedReview);
        assertEquals("Great item!", savedReview.getText());
        verify(reviewRepository, times(1)).save(any(Review.class));
    }
}