package com.tatiramos.mscartoes.application.dto.CartaoSaveRequest;

import com.tatiramos.mscartoes.domain.BandeiraCartao;
import com.tatiramos.mscartoes.domain.Cartao;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartaoSaveRequest {

    private String nome;
    private BandeiraCartao bandeira;
    private BigDecimal renda;
    private BigDecimal limite;

    public Cartao toModel(){
        return new Cartao(nome, bandeira, renda, limite);
    }
}
