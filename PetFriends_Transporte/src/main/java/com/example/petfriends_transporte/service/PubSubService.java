package com.example.petfriends_transporte.service;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PubSubService {

    private final PubSubTemplate pubSubTemplate;

    @Autowired
    public PubSubService(PubSubTemplate pubSubTemplate) {
        this.pubSubTemplate = pubSubTemplate;
    }

    private static final String TOPIC_NAME = "your-topic-name";

    public void enviarMensagem(String mensagem) {
        pubSubTemplate.publish(TOPIC_NAME, mensagem);
        System.out.println("Mensagem enviada para o tópico: " + mensagem);
    }

    public void receberMensagem(String mensagem) {
        System.out.println("Mensagem recebida: " + mensagem);
    }
}
