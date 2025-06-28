package com.neto.posts.postservice.domain.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class PostEntity {
    @Id
    @AttributeOverride(name = "value", column = @Column(
            name = "id", columnDefinition = "BIGINT"))
    private PostId id;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String body;
    private String author;
    private Integer wordCount;
    private Double calculatedValue;

}