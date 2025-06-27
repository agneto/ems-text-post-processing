package com.neto.posts.postservice.api.model;

import io.hypersistence.tsid.TSID;

public record PostSummaryOutput(
        TSID id,
        String title,
        String body,
        String summary,
        String author
) {
}
