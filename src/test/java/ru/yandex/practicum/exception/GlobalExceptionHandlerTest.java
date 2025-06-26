package ru.yandex.practicum.exception;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.user.UserController;
import ru.yandex.practicum.user.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {UserController.class, GlobalExceptionHandler.class})
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void handleUserNotFoundException_shouldReturn404() throws Exception {
        when(userService.getUserById(1L)).thenThrow(new UserNotFoundException("User not found"));

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{\"error\":\"Пользователь не найден\"}"));
    }

    @Test
    public void handleEmailAlreadyExistsException_shouldReturn409() throws Exception {
        when(userService.createUser(any())).thenThrow(new EmailAlreadyExistsException("Email already exists"));

        String userJson = "{\"name\":\"Test User\",\"email\":\"test@example.com\"}";

        mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content(userJson))
                .andExpect(status().isConflict())
                .andExpect(content().string("{\"error\":\"Email уже существует\"}"));
    }
}
