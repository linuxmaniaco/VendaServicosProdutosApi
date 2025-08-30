package com.VendaServicosProdutosApi.dto;

import lombok.Data;

import java.util.List;

@Data
public class SalesOrderDTO {
    private String customerName;
    private List<OrderItemDTO> orderItens;
}
