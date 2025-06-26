package ru.yandex.practicum.Reviews;

import lombok.Data;
import ru.yandex.practicum.user.User;
import ru.yandex.practicum.item.Item;

@Data
public class Review {
    private Long id;
    private String text;
    private User author;
    private Item item;
}
