package ru.yandex.practicum.Reviews;

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

@WebMvcTest(ReviewController.class)
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @Test
    public void createReview_shouldReturnCreatedReview() throws Exception {
        // Создаем объекты Item и User для Review
        Item item = new Item();
        item.setId(1L);

        User author = new User();
        author.setId(1L);

        Review review = new Review();
        review.setId(1L);
        review.setText("Great item!");
        review.setItem(item); // Устанавливаем item
        review.setAuthor(author); // Устанавливаем author

        when(reviewService.createReview(anyLong(), any(ReviewDto.class))).thenReturn(review);

        String reviewJson = "{\"text\":\"Great item!\",\"itemId\":1,\"authorId\":1}";

        mockMvc.perform(post("/reviews")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reviewJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.text").value("Great item!"))
                .andExpect(jsonPath("$.itemId").value(1L))
                .andExpect(jsonPath("$.authorId").value(1L));
    }
}