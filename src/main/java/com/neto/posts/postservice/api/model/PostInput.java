package com.neto.posts.postservice.api.model;

import jakarta.validation.constraints.NotBlank;

public record PostInput(
        @NotBlank String title,
        @NotBlank String body,
        @NotBlank String author) {
}
