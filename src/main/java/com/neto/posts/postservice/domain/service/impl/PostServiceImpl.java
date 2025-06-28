package com.neto.posts.postservice.domain.service.impl;

import com.neto.posts.postservice.api.model.PayloadOutput;
import com.neto.posts.postservice.api.model.PostInput;
import com.neto.posts.postservice.api.model.PostOutput;
import com.neto.posts.postservice.api.model.PostSummaryOutput;
import com.neto.posts.postservice.common.IdGenerator;
import com.neto.posts.postservice.domain.model.PostEntity;
import com.neto.posts.postservice.domain.model.PostId;
import com.neto.posts.postservice.domain.repository.PostRepository;
import com.neto.posts.postservice.domain.service.PostService;
import com.neto.posts.postservice.util.TextUtils;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static com.neto.posts.postservice.infrastructure.rabbitmq.RabbitMQConfig.DIRECT_EXCHANGE_NAME;
import static com.neto.posts.postservice.infrastructure.rabbitmq.RabbitMQConfig.ROUTING_KEY_PROCESS_POST;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository repository;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public PostOutput create(final PostInput input) {

        final PostEntity entity = new PostEntity();
        entity.setId(new PostId(IdGenerator.generateTSID()));
        entity.setAuthor(input.author());
        entity.setTitle(input.title());
        entity.setBody(input.body());

        final PostEntity saved = this.repository.save(entity);

        final MessagePostProcessor messagePostProcessor = message -> {
            message.getMessageProperties().setHeader("postId", saved.getId().toString());
            return message;
        };

        final var payload = new PayloadOutput(saved.getId().toString(), saved.getBody());

        this.rabbitTemplate.convertAndSend(
                DIRECT_EXCHANGE_NAME, ROUTING_KEY_PROCESS_POST, payload, messagePostProcessor);

        return new PostOutput(saved.getId().getValue(), saved.getTitle(),
                saved.getBody(), saved.getAuthor(), null, null);
    }

    @Override
    public Page<PostSummaryOutput> search(final Pageable pageable) {
        final Page<PostEntity> posts = this.repository.findAll(pageable);
        return posts.map(this::convertToModel);
    }

    @Override
    public PostOutput searchById(final TSID id) {
        final PostEntity postEntity = repository.findById(new PostId(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));


        return new PostOutput(postEntity.getId().getValue(), postEntity.getTitle(),
                postEntity.getBody(), postEntity.getAuthor(), postEntity.getWordCount(),
                postEntity.getCalculatedValue());
    }


    private PostSummaryOutput convertToModel(final PostEntity postEntity) {
        return new PostSummaryOutput(
                postEntity.getId().getValue(),
                postEntity.getTitle(),
                postEntity.getBody(),
                TextUtils.getFirstThreeSentences(postEntity.getBody()),
                postEntity.getAuthor());
    }
}
