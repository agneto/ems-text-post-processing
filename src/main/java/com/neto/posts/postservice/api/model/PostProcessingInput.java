package com.neto.posts.postservice.api.model;

public record PostProcessingInput(
        String postId,
        Integer wordCount,
        Double calculatedValue
) {
}
