package com.tatiramos.msavaliadorcredito.application;

import com.tatiramos.msavaliadorcredito.application.excepetion.DadosClienteNotFoundException;
import com.tatiramos.msavaliadorcredito.application.excepetion.ErroComunicacaoMicroservicesException;
import com.tatiramos.msavaliadorcredito.domain.model.CartaoCliente;
import com.tatiramos.msavaliadorcredito.domain.model.DadosCliente;
import com.tatiramos.msavaliadorcredito.domain.model.SituacaoCliente;
import com.tatiramos.msavaliadorcredito.infra.clients.CartoesResourceClient;
import com.tatiramos.msavaliadorcredito.infra.clients.ClienteResourceClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clientesClient;
    private final CartoesResourceClient cartoesClient;

    public SituacaoCliente obterSituacaoCliente (String cpf)
            throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException {
        // obter dados do cliente - MSCLIENTES
        // obter cartões do cliente - MSCARTOES

        ResponseEntity<DadosCliente> dadosClienteResponse = clientesClient.dadosCliente(cpf);
        try {
            ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesClient.getCertoesPorCliente(cpf);

            return SituacaoCliente
                    .builder()
                    .cliente(dadosClienteResponse.getBody())
                    .cartoes(cartoesResponse.getBody())
                    .build();
        }catch (FeignException.FeignClientException e){
            int status = e.status();
            if(HttpStatus.NOT_FOUND.value() == status){
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
        }
    }
}
