package com.example.petfriends_transporte.message;

import com.example.petfriends_transporte.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransporteMensagemPublicacao {

    private final RabbitTemplate rabbitTemplate;

    public TransporteMensagemPublicacao(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY, message);
    }
}
