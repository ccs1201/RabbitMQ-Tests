package com.ccs.rabbitmqtests.framework;

import com.ccs.rabbitmqtests.api.v1.handlers.annotations.EndpointImpl;
import com.ccs.rabbitmqtests.domain.core.exceptions.AppRuntimeException;
import com.ccs.rabbitmqtests.framework.exception.HandlerException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Component
@Slf4j
public final class EndpointResolverComponent {

    private final Map<String, Endpoint> handlersMap;
    private final ObjectMapper objectMapper;

    public EndpointResolverComponent(List<Endpoint> endpoints, ObjectMapper objectMapper) {
        this.handlersMap = getHandlersMap(endpoints);
        this.objectMapper = objectMapper;
    }

    private static Map<String, Endpoint> getHandlersMap(final List<Endpoint> endpoints) {
        return endpoints.stream()
                .collect(Collectors
                        .toMap(EndpointResolverComponent::getDeclaredForClass, Function.identity()));
    }

    private static String getDeclaredForClass(final Endpoint endpoint) {

        if (endpoint.getClass().isAnnotationPresent(EndpointImpl.class)) {
            return endpoint.getClass()
                    .getDeclaredAnnotation(EndpointImpl.class)
                    .forClass()
                    .getSimpleName();
        }
        throw new AppRuntimeException(endpoint.getClass().getName() + " is not annotated with @HandlerImpl");
    }

    @RabbitListener(queues = "${app.endpoint.queue}", concurrency = "${app.endpoint.concurrency}", returnExceptions = "true")
    public void onMessage(final Message message) {

        var typeId = message.getMessageProperties().getHeaders().get("__TypeId__").toString();

        if (nonNull(typeId)) {

            final var type = typeId.substring(typeId.lastIndexOf(".") + 1);

            var endpointToCall = Optional.empty();
            try {
                endpointToCall = Optional.ofNullable(handlersMap.get(type));
                endpointToCall.ifPresentOrElse(endpoint -> {
                    try {
                        var parameterType = endpoint.getClass().getAnnotation(EndpointImpl.class).forClass();
                        final var payload = objectMapper.readValue(message.getBody(), parameterType);

                        // Casting necessário para o tipo genérico
                        @SuppressWarnings("unchecked")
                        Endpoint<Object, Object> typedEndpoint = (Endpoint<Object, Object>) endpoint;
                        typedEndpoint.handle(payload);

                    } catch (ClassCastException e) {
                        log.error("Handler found but failed to cast: {}", endpoint.getClass().getSimpleName(), e);
                        throw new HandlerException("Handler casting failed", e);
                    } catch (Exception e) {
                        log.error("Handler #{} failed to process message", endpoint.getClass().getSimpleName(), e);
                        throw new HandlerException(e);
                    }
                }, () -> log.warn("No handler found for type: {}", typeId));

            } catch (Exception e) {
                log.error("Failed to process message with type: {}", typeId, e);
                throw new AppRuntimeException("Failed to process message", e);
            }
        } else {
            log.warn("Missing __TypeId__ header in the message");
            throw new HandlerException("Missing __TypeId__ header in the message");
        }
    }

}
