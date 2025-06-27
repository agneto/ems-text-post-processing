package com.neto.posts.postservice.domain.model;

import io.hypersistence.tsid.TSID;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostId {
    private TSID value;

    public PostId(final TSID value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public PostId(final Long value) {
        Objects.requireNonNull(value);
        this.value = TSID.from(value);
    }

    public PostId(final String value) {
        Objects.requireNonNull(value);
        this.value = TSID.from(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
