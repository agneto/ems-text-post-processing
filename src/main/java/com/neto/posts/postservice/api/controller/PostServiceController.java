package com.neto.posts.postservice.api.controller;

import com.neto.posts.postservice.api.model.PostInput;
import com.neto.posts.postservice.api.model.PostOutput;
import com.neto.posts.postservice.api.model.PostSummaryOutput;
import com.neto.posts.postservice.domain.service.PostService;
import io.hypersistence.tsid.TSID;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostServiceController {

    private final PostService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostOutput crate(@RequestBody @Valid final PostInput input) {
        return this.service.create(input);
    }

    @GetMapping
    public Page<PostSummaryOutput> search(@PageableDefault final Pageable pageable) {
        return this.service.search(pageable);
    }

    @GetMapping("/{postId}")
    public PostOutput searchById(@PathVariable("postId") TSID id) {
        return this.service.searchById(id);
    }
}
