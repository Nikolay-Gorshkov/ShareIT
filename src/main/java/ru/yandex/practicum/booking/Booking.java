package ru.yandex.practicum.booking;


import lombok.Data;
import java.time.LocalDateTime;
import ru.yandex.practicum.item.Item;
import ru.yandex.practicum.user.User;

@Data
public class Booking {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private Item item;
    private User booker;
    private BookingStatus status;
}
