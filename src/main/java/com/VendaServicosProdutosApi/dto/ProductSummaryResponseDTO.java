package com.VendaServicosProdutosApi.dto;

public record ProductSummaryResponseDTO(
        Long id,
        String name,
        String description,
        Double unit_Price,
        int stock_quantity,
        Integer minimumStock,
        Boolean active
) {}
