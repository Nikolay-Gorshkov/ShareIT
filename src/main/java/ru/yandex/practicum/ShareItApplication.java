package ru.yandex.practicum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.yandex.practicum.booking.BookingRepository;
import ru.yandex.practicum.booking.BookingService;
import ru.yandex.practicum.booking.BookingServiceImpl;
import ru.yandex.practicum.booking.InMemoryBookingRepository;
import ru.yandex.practicum.item.InMemoryItemRepository;
import ru.yandex.practicum.item.ItemRepository;
import ru.yandex.practicum.item.ItemService;
import ru.yandex.practicum.item.ItemServiceImpl;
import ru.yandex.practicum.Reviews.InMemoryReviewRepository;
import ru.yandex.practicum.Reviews.ReviewRepository;
import ru.yandex.practicum.Reviews.ReviewService;
import ru.yandex.practicum.Reviews.ReviewServiceImpl;
import ru.yandex.practicum.user.InMemoryUserRepository;
import ru.yandex.practicum.user.UserRepository;
import ru.yandex.practicum.user.UserService;
import ru.yandex.practicum.user.UserServiceImpl;

@SpringBootApplication
public class ShareItApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShareItApplication.class, args);
    }

    @Bean
    public UserRepository userRepository() {
        return new InMemoryUserRepository();
    }

    @Bean
    public UserService userService(UserRepository userRepository) {
        return new UserServiceImpl(userRepository);
    }

    @Bean
    public ItemRepository itemRepository() {
        return new InMemoryItemRepository();
    }

    @Bean
    public ItemService itemService(ItemRepository itemRepository, UserService userService) {
        return new ItemServiceImpl(itemRepository, userService);
    }

    @Bean
    public BookingRepository bookingRepository() {
        return new InMemoryBookingRepository();
    }

    @Bean
    public BookingService bookingService(BookingRepository bookingRepository, UserService userService, ItemService itemService) {
        return new BookingServiceImpl(bookingRepository, userService, itemService);
    }

    @Bean
    public ReviewRepository reviewRepository() {
        return new InMemoryReviewRepository();
    }

    @Bean
    public ReviewService reviewService(ReviewRepository reviewRepository, UserService userService, ItemService itemService) {
        return new ReviewServiceImpl(reviewRepository, userService, itemService);
    }
}