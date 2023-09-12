package com.tatiramos.msavaliadorcredito.application;

import com.tatiramos.msavaliadorcredito.domain.model.CartaoCliente;
import com.tatiramos.msavaliadorcredito.domain.model.DadosCliente;
import com.tatiramos.msavaliadorcredito.domain.model.SituacaoCliente;
import com.tatiramos.msavaliadorcredito.infra.clients.CartoesResourceClient;
import com.tatiramos.msavaliadorcredito.infra.clients.ClienteResourceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clientesClient;
    private final CartoesResourceClient cartoesClient;

    public SituacaoCliente obterSituacaoCliente (String cpf){
        // obter dados do cliente - MSCLIENTES
        // obter cartões do cliente - MSCARTOES

        ResponseEntity<DadosCliente> dadosClienteResponse = clientesClient.dadosCliente(cpf);
        ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesClient.getCertoesPorCliente(cpf);

        return SituacaoCliente
                .builder()
                .cliente(dadosClienteResponse.getBody())
                .cartoes(cartoesResponse.getBody())
                .build();
    }
}
