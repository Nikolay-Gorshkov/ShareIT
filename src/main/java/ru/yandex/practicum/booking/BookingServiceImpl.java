package ru.yandex.practicum.booking;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.exception.ItemNotFoundException;
import ru.yandex.practicum.exception.UserNotFoundException;
import ru.yandex.practicum.item.Item;
import ru.yandex.practicum.item.ItemService;
import ru.yandex.practicum.user.User;
import ru.yandex.practicum.user.UserService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final ItemService itemService;

    public BookingServiceImpl(BookingRepository bookingRepository, UserService userService, ItemService itemService) {
        this.bookingRepository = bookingRepository;
        this.userService = userService;
        this.itemService = itemService;
    }

    @Override
    public Booking createBooking(Long userId, BookingDto bookingDto) {
        if (bookingDto == null || bookingDto.getStart() == null || bookingDto.getEnd() == null || bookingDto.getItemId() == null) {
            throw new IllegalArgumentException("Booking data is incomplete");
        }
        if (bookingDto.getStart().isBefore(LocalDateTime.now()) || bookingDto.getEnd().isBefore(bookingDto.getStart())) {
            throw new IllegalArgumentException("Invalid booking dates");
        }
        try {
            User booker = userService.getUserById(userId);
            Item item = itemService.getItemById(bookingDto.getItemId());
            if (item.getAvailable() == null || !item.getAvailable()) {
                throw new IllegalArgumentException("Item is not available for booking");
            }
            Booking booking = new Booking();
            booking.setStart(bookingDto.getStart());
            booking.setEnd(bookingDto.getEnd());
            booking.setItem(item);
            booking.setBooker(booker);
            booking.setStatus(BookingStatus.WAITING);
            return bookingRepository.save(booking);
        } catch (UserNotFoundException | ItemNotFoundException e) {
            throw e;
        }
    }

    @Override
    public Booking updateBooking(Long bookingId, boolean approved, Long userId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        if (!booking.getItem().getOwner().getId().equals(userId)) {
            throw new RuntimeException("Only owner can approve booking");
        }
        booking.setStatus(approved ? BookingStatus.APPROVED : BookingStatus.REJECTED);
        return bookingRepository.save(booking);
    }

    @Override
    public Booking getBookingById(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    @Override
    public List<Booking> getBookingsByUser(Long userId) {
        return bookingRepository.findByBookerId(userId);
    }
}