package com.neto.posts.postservice.domain.repository;

import com.neto.posts.postservice.domain.model.PostEntity;
import com.neto.posts.postservice.domain.model.PostId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, PostId> {
}
