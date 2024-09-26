package com.example.petfriends_transporte.entity;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class EnderecoVO {

    private final String localOrigem;
    private final String localDestino;


        public EnderecoVO(String localOrigem, String localDestino) {
            this.localOrigem = localOrigem;
            this.localDestino = localDestino;
        }
        public EnderecoVO() {
            this.localOrigem = null;
            this.localDestino = null;
        }


    public String getLocalOrigem() {
        return localOrigem;
    }

    public String getLocalDestino() {
        return localDestino;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnderecoVO that = (EnderecoVO) o;
        return Objects.equals(localOrigem, that.localOrigem) &&
                Objects.equals(localDestino, that.localDestino);
    }

    @Override
    public int hashCode() {
        return Objects.hash(localOrigem, localDestino);
    }

    @Override
    public String toString() {
        return "Origem: " + localOrigem + ", Destino: " + localDestino;
    }
}
