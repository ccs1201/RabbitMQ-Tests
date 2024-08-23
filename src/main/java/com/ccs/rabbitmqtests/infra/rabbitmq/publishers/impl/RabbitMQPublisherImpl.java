package com.ccs.rabbitmqtests.infra.rabbitmq.publishers.impl;

import com.ccs.rabbitmqtests.domain.core.exceptions.AppRuntimeException;
import com.ccs.rabbitmqtests.framework.exception.HandlerException;
import com.ccs.rabbitmqtests.infra.rabbitmq.publishers.RabbitMQPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.RemoteInvocationResult;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static com.ccs.rabbitmqtests.domain.core.constants.AppConstants.RabbitMQConstants;

@Component
@Slf4j
@RequiredArgsConstructor
public class RabbitMQPublisherImpl implements RabbitMQPublisher {


    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendAsync(String routingKey, Object message) {
        sendAsync(RabbitMQConstants.EXCHANGE_NAME, routingKey, message);
    }

    @Override
    public void sendAsync(String exchange, String routingKey, Object message) {
        log(exchange, routingKey, message);
        CompletableFuture
                .runAsync(() -> rabbitTemplate
                        .convertAndSend(exchange, routingKey, message, getMessagePostProcessor()));
    }

    /**
     * <p>
     * Este método funciona como uma chamada RPC (remote procedure call) onde uma
     * mensagem é publicada em uma fila do RabbitMQ e é esperada uma resposta de
     * forma síncrona.
     * </p>
     * <p>
     * O método envia uma mensagem para a fila especificado pela chave de roteamento {@code routingKey}
     * e aguarda a resposta de volta do consumidor. O tempo máximo de espera é de 30 segundos.
     * Se nenhuma resposta for recebida dentro do tempo especificado, um erro é lançado.
     * </p>
     * <p>
     * O método retorna o objeto de resposta do tipo especificado em {@code responseClass}.
     * </p>
     *
     * @param routingKey    A RoutingKey da fila.
     * @param message       A mensagem que será enviada/publicada.
     * @param responseClass O tipo de resposta esperada.
     * @return A response convertida para o tipo definido em responseClass.
     * @throws HandlerException    Se ocorrer um erro de processamento no destino.
     * @throws AppRuntimeException Se ocorrer um erro ao enviar ou receber a mensagem do destino.
     */
    public <T> T call(String routingKey, Object message, Class<T> responseClass) {
        log(RabbitMQConstants.EXCHANGE_NAME, routingKey, message);

        try {
            var response = Optional.ofNullable(
                    rabbitTemplate
                            .convertSendAndReceive(RabbitMQConstants.EXCHANGE_NAME,
                                    routingKey,
                                    message,
                                    getMessagePostProcessor()
                            )
            );

            if (response.isPresent() && response.get() instanceof RemoteInvocationResult result && result.hasException()) {
                throw new HandlerException(result.getException());
            }

            return responseClass.cast(response.orElseThrow(() -> new AppRuntimeException("No response was received for message")));

        } catch (Exception e) {
            throw new AppRuntimeException(e.getMessage(), e);
        }
    }

    private static MessagePostProcessor getMessagePostProcessor() {
        return messagePostProcessor -> {
            messagePostProcessor.getMessageProperties().setHeader("offSetDateTime", OffsetDateTime.now());
            messagePostProcessor.getMessageProperties().setMessageId(UUID.randomUUID().toString());
            messagePostProcessor.getMessageProperties().setTimestamp(Date.from(OffsetDateTime.now().toInstant()));
            messagePostProcessor.getMessageProperties().setReplyTo(RabbitMQConstants.EXCHANGE_NAME.concat(".reply-to"));
            messagePostProcessor.getMessageProperties().setDelayLong(5L);

            return messagePostProcessor;
        };
    }

    private static void log(String exchange, String routingKey, Object message) {
        log.info("""
                
                Sending message to RabbitMQ
                Exchange: {}
                RoutigKey: {}
                message: {}
                """, exchange, routingKey, message);
    }
}
