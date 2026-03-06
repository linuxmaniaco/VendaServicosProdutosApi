package com.VendaServicosProdutosApi.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PrintServiceDTO {
    private String name;
    private Integer quantity;
    private BigDecimal price;
    private String itemType;
}
