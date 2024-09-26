package com.example.petfriends_almoxarifado.controller;

import com.example.petfriends_almoxarifado.config.RabbitConfig;
import com.example.petfriends_almoxarifado.domain.Estoque;
import com.example.petfriends_almoxarifado.service.EstoqueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

import java.util.List;

@RestController
@RequestMapping("/api/estoques")
public class EstoqueController {

    private static final Logger logger = Logger.getLogger(EstoqueController.class.getName());

    private final EstoqueService estoqueService;

    private final RabbitConfig rabbitConfig;

    public EstoqueController(EstoqueService estoqueService, RabbitConfig rabbitConfig) {
        this.estoqueService = estoqueService;
        this.rabbitConfig = rabbitConfig;
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<List<Estoque>> getAllEstoques() {

        return estoqueService.listarEstoque()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/adicionar")
    public ResponseEntity<Estoque> adicionarEstoque(@RequestBody Estoque estoque) {
        logger.info("adicionando estoque " + estoque.getProdutoId() + estoque.getLocalizacao());
        return ResponseEntity.ok(estoqueService.adicionarEstoque(estoque));
    }

    @PostMapping("/remover")
    public ResponseEntity<Estoque> removerEstoque(@RequestBody Estoque estoque) {
        return ResponseEntity.ok(estoque);
    }

    @GetMapping("/{produtoId}")
    public ResponseEntity<Estoque> obterEstoquePorProdutoId(@PathVariable Long produtoId) {
        return estoqueService.obterEstoquePorProdutoId(produtoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/enviar")
    public ResponseEntity<String> processarPedido(@RequestBody String payload) {
        estoqueService.processarPedido(payload);
        return new ResponseEntity<>("Pedido processado com sucesso!", HttpStatus.OK);
    }
}
