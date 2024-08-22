package com.ccs.rabbitmqtests.framework;

import com.ccs.rabbitmqtests.api.v1.handlers.annotations.HandlerImpl;
import com.ccs.rabbitmqtests.domain.core.exceptions.AppRuntimeException;
import com.ccs.rabbitmqtests.framework.exception.HandlerException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Component
@Slf4j
public final class HandlerResolverComponent {

    private final Map<String, Handler> handlersMap;
    private final ObjectMapper objectMapper;

    @Autowired
    public HandlerResolverComponent(List<Handler> handlers, ObjectMapper objectMapper) {
        this.handlersMap = getHandlersMap(handlers);
        this.objectMapper = objectMapper;
    }

    private static Map<String, Handler> getHandlersMap(final List<Handler> handlers) {
        return handlers.stream()
                .collect(Collectors
                        .toMap(HandlerResolverComponent::getDeclaredClass, Function.identity()));
    }

    private static String getDeclaredClass(final Handler handler) {

        if (handler.getClass().isAnnotationPresent(HandlerImpl.class)) {
            return handler.getClass()
                    .getDeclaredAnnotation(HandlerImpl.class)
                    .value()
                    .getSimpleName();
        }
        throw new AppRuntimeException(handler.getClass().getName() + " is not annotated with @HandlerImpl");
    }

    @RabbitListener(queues = "${service.handler.queue}", concurrency = "${service.handler.concurrency}", returnExceptions = "true")
    public void onMessage(final Message message) {
        final var typeId = message.getMessageProperties().getHeaders().get("__TypeId__").toString();
        if (nonNull(typeId)) {
            Optional<Handler> handler = Optional.empty();
            try {
                final var type = Class.forName(typeId);
                final var payload = objectMapper.readValue(message.getBody(), type);
                handler = Optional.ofNullable(handlersMap.get(type.getSimpleName()));

                handler.ifPresentOrElse(h -> h.handle(payload),
                        () -> log.warn("No handler found for type: {}", type.getSimpleName()));
            } catch (ClassNotFoundException | IOException e) {
                log.error("Failed to process message with type: {}", typeId, e);
                throw new AppRuntimeException("Failed to process message", e);
            } catch (Exception e) {
                handler.ifPresent(h -> log.error("Handler #{} failed to process message", h.getClass().getSimpleName(), e));
                throw new HandlerException(e);
            }

        } else {
            log.warn("Missing __TypeId__ header in the message");
            throw new HandlerException("Missing __TypeId__ header in the message");
        }
    }
}
