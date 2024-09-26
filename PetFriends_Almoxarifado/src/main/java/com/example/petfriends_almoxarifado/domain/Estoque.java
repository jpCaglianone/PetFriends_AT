package com.example.petfriends_almoxarifado.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import com.example.petfriends_almoxarifado.domain.LocalizacaoVO;

@Entity
@Table(name="estoque")
public class Estoque implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private Long produtoId;
    private int quantidadeAtual;
    @Embedded
    private LocalizacaoVO localizacao;
    private LocalDateTime dataUltimaAtualizacao;


//    public Estoque(Long produtoId, int quantidade, LocalizacaoVO localizacao) {
//        this.produtoId = Objects.requireNonNull(produtoId, "ProdutoId não pode ser nulo");
//        this.quantidadeAtual = quantidade;
//        this.dataUltimaAtualizacao = LocalDateTime.now();
//    }

    public Estoque() {
        this.dataUltimaAtualizacao = LocalDateTime.now();
    }

    public void adicionar(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade a adicionar deve ser positiva.");
        }
        this.quantidadeAtual += quantidade;
        this.dataUltimaAtualizacao = LocalDateTime.now();
    }

    public void remover(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade a remover deve ser positiva.");
        }
        if (quantidade > this.quantidadeAtual) {
            throw new IllegalStateException("Não é possível remover mais do que o disponível em estoque.");
        }
        this.quantidadeAtual -= quantidade;
        this.dataUltimaAtualizacao = LocalDateTime.now();
    }


    public Long getId() {
        return id;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public int getQuantidadeAtual() {
        return quantidadeAtual;
    }


    public LocalDateTime getDataUltimaAtualizacao() {
        return dataUltimaAtualizacao;
    }

    public LocalizacaoVO getLocalizacao() {
        return localizacao;
    }
    public void setLocalizacao(LocalizacaoVO localizacao) {
        this.localizacao = localizacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Estoque estoque = (Estoque) o;
        return Objects.equals(produtoId, estoque.produtoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(produtoId);
    }
}
