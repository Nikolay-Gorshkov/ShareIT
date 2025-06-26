package ru.yandex.practicum.booking;

public class BookingMapper {
    public static BookingDto toBookingDto(Booking booking) {
        if (booking == null) {
            return null;
        }
        BookingDto dto = new BookingDto();
        dto.setId(booking.getId());
        dto.setStart(booking.getStart());
        dto.setEnd(booking.getEnd());
        dto.setItemId(booking.getItem() != null ? booking.getItem().getId() : null);
        dto.setBookerId(booking.getBooker() != null ? booking.getBooker().getId() : null);
        dto.setStatus(booking.getStatus());
        return dto;
    }
}