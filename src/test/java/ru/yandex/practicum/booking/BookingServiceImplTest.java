package ru.yandex.practicum.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.yandex.practicum.item.Item;
import ru.yandex.practicum.item.ItemService;
import ru.yandex.practicum.user.User;
import ru.yandex.practicum.user.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private UserService userService;

    @Mock
    private ItemService itemService;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createBooking_shouldSaveBooking() {
        User user = new User();
        user.setId(1L);

        Item item = new Item();
        item.setId(1L);
        item.setAvailable(true); // Исправление: устанавливаем available

        BookingDto bookingDto = new BookingDto();
        bookingDto.setItemId(1L);
        bookingDto.setStart(java.time.LocalDateTime.now().plusDays(1));
        bookingDto.setEnd(java.time.LocalDateTime.now().plusDays(2));

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setStatus(BookingStatus.WAITING);

        when(userService.getUserById(1L)).thenReturn(user);
        when(itemService.getItemById(1L)).thenReturn(item);
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        Booking savedBooking = bookingService.createBooking(1L, bookingDto);

        assertNotNull(savedBooking);
        assertEquals(BookingStatus.WAITING, savedBooking.getStatus());
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }
}
