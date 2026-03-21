package com.VendaServicosProdutosApi.service;

import com.VendaServicosProdutosApi.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EstoqueNotificacaoService {

    /**
     * Verifica se o produto atingiu o estoque mínimo e registra um alerta.
     * TODO: implementar envio de e-mail quando o estoque atingir o mínimo.
     */
    public void verificarEstoqueMinimo(Product product) {
        if (product.getStock_quantity() <= product.getMinimumStock()) {
            log.warn("ALERTA DE ESTOQUE MÍNIMO: produto '{}' (id={}) com {} unidade(s) — mínimo configurado: {}",
                    product.getName(),
                    product.getId(),
                    product.getStock_quantity(),
                    product.getMinimumStock());

            // TODO: chamar serviço de e-mail aqui
            // emailService.enviarAlertaEstoqueMinimo(product);
        }
    }
}