package com.VendaServicosProdutosApi.dto.responseDTO;

public record ProductResponseDTO(

        Long id,
        String name,
        String description,
        Double unit_Price,
        int stock_quantity,
        Boolean active
){}
