package com.example.petfriends_transporte.controller;

import com.example.petfriends_transporte.entity.Entrega;
import com.example.petfriends_transporte.service.EntregaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entregas")
public class EntregaController {

    private final EntregaService entregaService;

    public EntregaController(EntregaService entregaService) {
        this.entregaService = entregaService;
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<List<Entrega>> listarEntregas() {
        return entregaService.listarEntregas().map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/adicionar")
    public ResponseEntity<Entrega> adicionarEntrega(@RequestBody Entrega entrega) {

        return new ResponseEntity<>(entregaService.salvarEntrega(entrega), HttpStatus.CREATED);
    }

    @PostMapping("/enviar")
    public ResponseEntity<String> processarPedido(@RequestBody String payload) {
        entregaService.processarPedido(payload);
        return new ResponseEntity<>("Pedido processado com sucesso!", HttpStatus.OK);
    }
}
