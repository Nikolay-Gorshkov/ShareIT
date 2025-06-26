package ru.yandex.practicum.Reviews;

public class ReviewMapper {
    public static ReviewDto toReviewDto(Review review) {
        if (review == null) {
            return null;
        }
        ReviewDto dto = new ReviewDto();
        dto.setId(review.getId());
        dto.setText(review.getText());
        dto.setAuthorId(review.getAuthor() != null ? review.getAuthor().getId() : null);
        dto.setItemId(review.getItem() != null ? review.getItem().getId() : null);
        return dto;
    }
}