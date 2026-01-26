package com.VendaServicosProdutosApi.dto;

import java.math.BigDecimal;

public record OrderItensResponseDTO(
        Long id,
        Integer quantity,
        BigDecimal unitValueAtTimeOfSale,
        BigDecimal totalItemValue,
        Long salesOrder,
        Long product,
        Long printService,
        String itemType
){}
