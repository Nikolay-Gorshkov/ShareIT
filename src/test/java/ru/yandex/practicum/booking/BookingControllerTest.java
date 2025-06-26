package ru.yandex.practicum.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.item.Item;
import ru.yandex.practicum.user.User;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @Test
    public void createBooking_shouldReturnCreatedBooking() throws Exception {
        // Создаем объекты Item и User для Booking
        Item item = new Item();
        item.setId(1L);
        item.setAvailable(true);

        User booker = new User();
        booker.setId(1L);

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setStart(java.time.LocalDateTime.now().plusDays(1));
        booking.setEnd(java.time.LocalDateTime.now().plusDays(2));
        booking.setStatus(BookingStatus.WAITING);
        booking.setItem(item); // Устанавливаем item
        booking.setBooker(booker); // Устанавливаем booker

        when(bookingService.createBooking(anyLong(), any(BookingDto.class))).thenReturn(booking);

        String bookingJson = "{\"start\":\"2025-06-27T09:00:00\",\"end\":\"2025-06-28T09:00:00\",\"itemId\":1}";

        mockMvc.perform(post("/bookings")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookingJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.status").value("WAITING"))
                .andExpect(jsonPath("$.itemId").value(1L))
                .andExpect(jsonPath("$.bookerId").value(1L));
    }
}