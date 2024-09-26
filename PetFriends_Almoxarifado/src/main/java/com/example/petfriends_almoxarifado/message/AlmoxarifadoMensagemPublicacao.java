package com.example.petfriends_almoxarifado.message;


import com.example.petfriends_almoxarifado.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class AlmoxarifadoMensagemPublicacao {

    private final RabbitTemplate rabbitTemplate;

    public AlmoxarifadoMensagemPublicacao(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY, message);
    }
}
