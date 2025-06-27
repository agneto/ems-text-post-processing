package com.neto.posts.postservice.domain.service;

import com.neto.posts.postservice.api.model.PostInput;
import com.neto.posts.postservice.api.model.PostOutput;
import com.neto.posts.postservice.api.model.PostSummaryOutput;
import io.hypersistence.tsid.TSID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    PostOutput create(PostInput input);

    Page<PostSummaryOutput> search(Pageable pageable);

    PostOutput searchById(TSID id);


}
