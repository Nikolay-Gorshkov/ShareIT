package ru.yandex.practicum.booking;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingDto> createBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                    @RequestBody BookingDto bookingDto) {
        Booking booking = bookingService.createBooking(userId, bookingDto);
        return new ResponseEntity<>(BookingMapper.toBookingDto(booking), HttpStatus.CREATED);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<BookingDto> updateBooking(@PathVariable Long bookingId,
                                                    @RequestParam boolean approved,
                                                    @RequestHeader("X-Sharer-User-Id") Long userId) {
        Booking booking = bookingService.updateBooking(bookingId, approved, userId);
        return ResponseEntity.ok(BookingMapper.toBookingDto(booking));
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDto> getBookingById(@PathVariable Long bookingId) {
        Booking booking = bookingService.getBookingById(bookingId);
        return ResponseEntity.ok(BookingMapper.toBookingDto(booking));
    }

    @GetMapping
    public ResponseEntity<List<BookingDto>> getBookingsByUser(@RequestHeader("X-Sharer-User-Id") Long userId) {
        List<BookingDto> bookings = bookingService.getBookingsByUser(userId).stream()
                .map(BookingMapper::toBookingDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bookings);
    }
}
