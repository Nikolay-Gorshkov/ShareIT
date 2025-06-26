package ru.yandex.practicum.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.item.Item;
import ru.yandex.practicum.user.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryBookingRepositoryTest {

    private InMemoryBookingRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryBookingRepository();
    }

    @Test
    void saveBooking_shouldAssignIdAndStore() {
        Booking booking = new Booking();
        booking.setStart(java.time.LocalDateTime.now());
        booking.setEnd(java.time.LocalDateTime.now().plusDays(1));
        booking.setStatus(BookingStatus.WAITING);
        booking.setBooker(new User());
        booking.setItem(new Item());

        Booking savedBooking = repository.save(booking);

        assertNotNull(savedBooking.getId());
        assertEquals(BookingStatus.WAITING, savedBooking.getStatus());
    }

    @Test
    void findByBookerId_shouldReturnBookings() {
        User user = new User();
        user.setId(1L);

        Booking booking = new Booking();
        booking.setBooker(user);
        booking.setStatus(BookingStatus.WAITING);
        repository.save(booking);

        List<Booking> bookings = repository.findByBookerId(1L);

        assertEquals(1, bookings.size());
        assertEquals(BookingStatus.WAITING, bookings.get(0).getStatus());
    }
}