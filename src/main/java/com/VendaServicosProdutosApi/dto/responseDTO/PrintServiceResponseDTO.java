package com.VendaServicosProdutosApi.dto.responseDTO;

public record PrintServiceResponseDTO(

        Long id,
        String name,
        String description,
        Double price,
        boolean active
){}
