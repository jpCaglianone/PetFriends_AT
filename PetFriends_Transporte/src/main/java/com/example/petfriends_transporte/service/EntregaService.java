package com.example.petfriends_transporte.service;

import com.example.petfriends_transporte.config.RabbitConfig;
import com.example.petfriends_transporte.entity.Entrega;
import com.example.petfriends_transporte.entity.Pedido;
import com.example.petfriends_transporte.repository.EntregaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class EntregaService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final Random random = new Random();
    private EntregaRepository entregaRepository;

    public EntregaService(EntregaRepository entregaRepository) {
        this.entregaRepository = entregaRepository;
    }

    public Optional<List<Entrega>> listarEntregas () {
        return Optional.ofNullable(entregaRepository.findAll());
    }

    public Optional<Entrega> buscarEntregaPorId(Long id) {
            return entregaRepository.findById(id);
    }

    public Entrega salvarEntrega(Entrega entrega) {
        return entregaRepository.save(entrega);
    }


    public void processarPedido(String payload) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Pedido pedido = objectMapper.readValue(payload, Pedido.class);

            String novoEstado = random.nextBoolean() ? "entregue" : "extraviado";
            pedido.setEstado(novoEstado);

            String novaMensagem = objectMapper.writeValueAsString(pedido);
            rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY, novaMensagem);

            System.out.println("Mensagem enviada com novo estado: " + novaMensagem);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao processar o payload: " + payload);
        }
    }


    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void receberPedido(String mensagem) {
        System.out.println("Mensagem recebida do serviço de pedido (pub/sub): " + mensagem);
        processarPedido(mensagem);
    }


}
