package com.neto.posts.postservice.infrastructure.rabbitmq;

import com.neto.posts.postservice.api.model.PostProcessingInput;
import com.neto.posts.postservice.domain.model.PostEntity;
import com.neto.posts.postservice.domain.model.PostId;
import com.neto.posts.postservice.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

import static com.neto.posts.postservice.infrastructure.rabbitmq.RabbitMQConfig.QUEUE_POST_PROCESSOR;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMQListener {

    private final PostRepository repository;

    @RabbitListener(queues = QUEUE_POST_PROCESSOR, concurrency = "2-3")
    public void handleProcessingTemperature(@Payload final PostProcessingInput postProcessingInput,
                                            @Headers final Map<String, Object> headers) {
        log.info("Headers ::::: {}", headers);

        final var postId = postProcessingInput.postId();

        final Optional<PostEntity> postEntityOptional = repository.findById(new PostId(postId));

        if (postEntityOptional.isPresent()) {
            final PostEntity postEntity = postEntityOptional.get();
            postEntity.setWordCount(postProcessingInput.wordCount());
            postEntity.setCalculatedValue(postProcessingInput.calculatedValue());

            repository.save(postEntity);
        }
    }
}
