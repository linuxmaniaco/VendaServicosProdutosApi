package com.VendaServicosProdutosApi.dto;

import com.VendaServicosProdutosApi.model.PrintService;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class SalesReportDTO {

    private Long orderId;
    private String customer;
    private LocalDateTime dateHour;
    private BigDecimal totalValueOrder;
    private List<OrderItensDTO> orderItens;
}
