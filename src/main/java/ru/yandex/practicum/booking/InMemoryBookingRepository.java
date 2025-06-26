package ru.yandex.practicum.booking;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryBookingRepository implements BookingRepository {
    private final List<Booking> bookings = new ArrayList<>();
    private Long nextId = 1L;

    @Override
    public Booking save(Booking booking) {
        if (booking.getId() == null) {
            booking.setId(nextId++);
        }
        bookings.removeIf(b -> b.getId().equals(booking.getId()));
        bookings.add(booking);
        return booking;
    }

    @Override
    public Optional<Booking> findById(Long id) {
        return bookings.stream().filter(b -> b.getId().equals(id)).findFirst();
    }

    @Override
    public List<Booking> findByBookerId(Long bookerId) {
        return bookings.stream().filter(b -> b.getBooker().getId().equals(bookerId)).collect(Collectors.toList());
    }
}
