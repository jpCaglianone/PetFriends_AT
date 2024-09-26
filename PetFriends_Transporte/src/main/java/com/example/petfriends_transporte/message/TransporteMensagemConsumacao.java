package com.example.petfriends_transporte.message;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.example.petfriends_transporte.config.RabbitConfig;

@Component
public class TransporteMensagemConsumacao {

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void receiveMessage(String message) {
        System.out.println("Mensagem recebida: " + message);
    }
}

