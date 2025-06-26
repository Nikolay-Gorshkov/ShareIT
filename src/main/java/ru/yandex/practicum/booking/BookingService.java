package ru.yandex.practicum.booking;

import java.util.List;

public interface BookingService {
    Booking createBooking(Long userId, BookingDto bookingDto);
    Booking updateBooking(Long bookingId, boolean approved, Long userId);
    Booking getBookingById(Long bookingId);
    List<Booking> getBookingsByUser(Long userId);
}
