package com.VendaServicosProdutosApi.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductItemDTO {
    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private String itemType;
}
