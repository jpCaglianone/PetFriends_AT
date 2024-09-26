package com.example.petfriends_almoxarifado.domain;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class LocalizacaoVO {

    private final String corredor;
    private final String prateleira;
    private final String secao;

    public LocalizacaoVO(String corredor, String prateleira, String secao) {
        this.corredor = corredor;
        this.prateleira = prateleira;
        this.secao = secao;
    }
    public LocalizacaoVO() {
        this.corredor = null;
        this.prateleira = null;
        this.secao = null;
    }


    public String getCorredor() {
        return corredor;
    }

    public String getPrateleira() {
        return prateleira;
    }

    public String getSecao() {
        return secao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocalizacaoVO that = (LocalizacaoVO) o;
        return Objects.equals(corredor, that.corredor) &&
                Objects.equals(prateleira, that.prateleira) &&
                Objects.equals(secao, that.secao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(corredor, prateleira, secao);
    }

    @Override
    public String toString() {
        return "Localizacao{" +
                "corredor='" + corredor + '\'' +
                ", prateleira='" + prateleira + '\'' +
                ", secao='" + secao + '\'' +
                '}';
    }
}
