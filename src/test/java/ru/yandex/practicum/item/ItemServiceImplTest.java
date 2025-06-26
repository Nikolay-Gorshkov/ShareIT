package ru.yandex.practicum.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.yandex.practicum.user.User;
import ru.yandex.practicum.user.UserService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private ItemServiceImpl itemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createItem_shouldSaveItem() {
        User user = new User();
        user.setId(1L);

        Item item = new Item();
        item.setName("Drill");
        item.setDescription("Powerful drill");
        item.setAvailable(true);

        when(userService.getUserById(1L)).thenReturn(user);
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        Item savedItem = itemService.createItem(1L, item);

        assertNotNull(savedItem);
        assertEquals("Drill", savedItem.getName());
        verify(itemRepository, times(1)).save(item);
    }

    @Test
    void searchItems_shouldReturnAvailableItems() {
        Item item = new Item();
        item.setName("Drill");
        item.setDescription("Powerful drill");
        item.setAvailable(true);

        when(itemRepository.search("drill")).thenReturn(List.of(item));

        List<Item> items = itemService.searchItems("drill");

        assertEquals(1, items.size());
        assertEquals("Drill", items.get(0).getName());
    }
}
