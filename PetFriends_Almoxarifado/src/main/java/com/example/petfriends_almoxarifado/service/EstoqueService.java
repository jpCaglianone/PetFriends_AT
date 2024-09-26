package com.example.petfriends_almoxarifado.service;

import com.example.petfriends_almoxarifado.config.RabbitConfig;
import com.example.petfriends_almoxarifado.domain.Estoque;
import com.example.petfriends_almoxarifado.domain.Pedido;
import com.example.petfriends_almoxarifado.repository.EstoqueRepository;
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
public class EstoqueService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final Random random = new Random();
    private final EstoqueRepository estoqueRepository;

    public EstoqueService(EstoqueRepository estoqueRepository) {
        this.estoqueRepository = estoqueRepository;
    }

    public Estoque adicionarEstoque(Estoque estoque) {
        return estoqueRepository.save(estoque);
    }

    public Estoque removerEstoque(Estoque estoque) {
        estoque.remover(estoque.getQuantidadeAtual());
        return estoqueRepository.save(estoque);
    }

    public Optional<Estoque> obterEstoquePorProdutoId(Long produtoId) {
        return estoqueRepository.findByProdutoId(produtoId);
    }

    public Optional<List<Estoque>> listarEstoque() {
        return Optional.of(estoqueRepository.findAll());
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
