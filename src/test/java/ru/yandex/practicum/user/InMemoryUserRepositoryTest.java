package ru.yandex.practicum.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryUserRepositoryTest {

    private InMemoryUserRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryUserRepository();
    }

    @Test
    void saveUser_shouldAssignIdAndStore() {
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");

        User savedUser = repository.save(user);

        assertNotNull(savedUser.getId());
        assertEquals("Test User", savedUser.getName());
        assertEquals("test@example.com", savedUser.getEmail());
    }

    @Test
    void findById_shouldReturnUser() {
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        repository.save(user);

        Optional<User> foundUser = repository.findById(user.getId());

        assertTrue(foundUser.isPresent());
        assertEquals("Test User", foundUser.get().getName());
    }

    @Test
    void findAll_shouldReturnAllUsers() {
        User user1 = new User();
        user1.setName("User 1");
        user1.setEmail("user1@example.com");
        User user2 = new User();
        user2.setName("User 2");
        user2.setEmail("user2@example.com");

        repository.save(user1);
        repository.save(user2);

        List<User> users = repository.findAll();

        assertEquals(2, users.size());
    }
}