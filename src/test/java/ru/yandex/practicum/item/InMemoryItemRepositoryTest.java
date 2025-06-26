package ru.yandex.practicum.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryItemRepositoryTest {

    private InMemoryItemRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryItemRepository();
    }

    @Test
    void saveItem_shouldAssignIdAndStore() {
        Item item = new Item();
        item.setName("Drill");
        item.setDescription("Powerful drill");
        item.setAvailable(true);

        Item savedItem = repository.save(item);

        assertNotNull(savedItem.getId());
        assertEquals("Drill", savedItem.getName());
    }

    @Test
    void search_shouldReturnMatchingItems() {
        Item item = new Item();
        item.setName("Drill");
        item.setDescription("Powerful drill");
        item.setAvailable(true);
        repository.save(item);

        List<Item> items = repository.search("drill");

        assertEquals(1, items.size());
        assertEquals("Drill", items.get(0).getName());
    }
}