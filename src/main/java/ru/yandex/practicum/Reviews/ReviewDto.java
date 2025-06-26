package ru.yandex.practicum.Reviews;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class ReviewDto {
    private Long id;

    @NotBlank(message = "Review text cannot be empty")
    private String text;

    @NotNull(message = "Author ID is required")
    private Long authorId;

    @NotNull(message = "Item ID is required")
    private Long itemId;
}