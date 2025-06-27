package com.neto.posts.postservice.api.model;

import io.hypersistence.tsid.TSID;

public record PostOutput(
        TSID id,
        String title,
        String body,
        String author,
        Integer wordCount,
        Double calculatedValue
) {
}
