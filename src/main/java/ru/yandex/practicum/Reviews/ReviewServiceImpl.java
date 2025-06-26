package ru.yandex.practicum.Reviews;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.exception.ItemNotFoundException;
import ru.yandex.practicum.exception.UserNotFoundException;
import ru.yandex.practicum.item.Item;
import ru.yandex.practicum.item.ItemService;
import ru.yandex.practicum.user.User;
import ru.yandex.practicum.user.UserService;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final ItemService itemService;

    public ReviewServiceImpl(ReviewRepository reviewRepository, UserService userService, ItemService itemService) {
        this.reviewRepository = reviewRepository;
        this.userService = userService;
        this.itemService = itemService;
    }

    @Override
    public Review createReview(Long userId, ReviewDto reviewDto) {
        if (reviewDto == null || reviewDto.getText() == null || reviewDto.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("Review text cannot be empty");
        }
        if (reviewDto.getItemId() == null) {
            throw new IllegalArgumentException("Item ID is required");
        }
        try {
            User author = userService.getUserById(userId);
            Item item = itemService.getItemById(reviewDto.getItemId());
            Review review = new Review();
            review.setText(reviewDto.getText());
            review.setAuthor(author);
            review.setItem(item);
            return reviewRepository.save(review);
        } catch (UserNotFoundException | ItemNotFoundException e) {
            throw e; // Пропускаем для обработки в GlobalExceptionHandler
        }
    }
}