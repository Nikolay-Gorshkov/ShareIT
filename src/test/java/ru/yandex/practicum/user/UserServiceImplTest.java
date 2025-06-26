package ru.yandex.practicum.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.yandex.practicum.exception.EmailAlreadyExistsException;
import ru.yandex.practicum.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_shouldSaveUser() {
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");

        when(userRepository.findAll()).thenReturn(List.of());
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.createUser(user);

        assertNotNull(savedUser);
        assertEquals("Test User", savedUser.getName());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void createUser_withExistingEmail_shouldThrowException() {
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");

        User existingUser = new User();
        existingUser.setEmail("test@example.com");

        when(userRepository.findAll()).thenReturn(List.of(existingUser));

        assertThrows(EmailAlreadyExistsException.class, () -> userService.createUser(user));
    }

    @Test
    void getUserById_notFound_shouldThrowException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));
    }
}
