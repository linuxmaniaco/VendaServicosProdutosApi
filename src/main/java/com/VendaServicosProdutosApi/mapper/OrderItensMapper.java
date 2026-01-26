package com.VendaServicosProdutosApi.mapper;

import com.VendaServicosProdutosApi.dto.OrderItensResponseDTO;
import com.VendaServicosProdutosApi.model.OrderItens;

public class OrderItensMapper {

    public static OrderItensResponseDTO toDTO(OrderItens item){
        return new OrderItensResponseDTO(
                item.getId(),
                item.getQuantity(),
                item.getUnitValueAtTimeOfSale(),
                item.getTotalItemValue(),
                item.getSalesOrder() != null ? item.getSalesOrder().getId() : null,
                item.getProduct() != null ? item.getProduct().getId() : null,
                item.getPrintService() != null ? item.getPrintService().getId() : null,
                item.getItemType().name()
        );
    }
}
