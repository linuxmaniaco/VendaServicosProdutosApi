package com.VendaServicosProdutosApi.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

//CLASSE DTO PROVAVELMENTE NÃO USANDO.
@Getter
@Setter
public class OrderItensDTO {
    private long id;
    private String name;
    private Integer quantity;
    private BigDecimal price;
}
