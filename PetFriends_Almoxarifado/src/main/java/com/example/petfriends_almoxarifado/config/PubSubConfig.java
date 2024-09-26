package com.example.petfriends_almoxarifado.config;

import com.example.petfriends_almoxarifado.service.EstoqueService;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import com.google.cloud.spring.pubsub.support.PublisherFactory;
import com.google.cloud.spring.pubsub.support.SubscriberFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
public class PubSubConfig {

    @Autowired
    private PubSubTemplate pubSubTemplate;

    @Autowired
    EstoqueService estoqueService;

    @Bean
    public MessageChannel pubsubInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public PubSubInboundChannelAdapter messageChannelAdapter(
            @Qualifier("pubsubInputChannel") MessageChannel inputChannel) {
        PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubSubTemplate, "your-subscription-name");
        adapter.setOutputChannel(inputChannel);
        return adapter;
    }




    @Bean
    @ServiceActivator(inputChannel = "pubsubInputChannel")
    public MessageHandler messageReceiver(EstoqueService estoqueService) {
        return message -> {
            String payload = (String) message.getPayload();
            System.out.println("Mensagem recebida do Google Pub/Sub: " + payload);

            estoqueService.processarPedido(payload);
        };
    }
}
