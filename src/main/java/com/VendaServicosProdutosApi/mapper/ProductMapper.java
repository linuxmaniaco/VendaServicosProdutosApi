package com.VendaServicosProdutosApi.mapper;

import com.VendaServicosProdutosApi.dto.requestDTO.ProductCreateRequestDTO;
import com.VendaServicosProdutosApi.dto.responseDTO.ProductResponseDTO;
import com.VendaServicosProdutosApi.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductCreateRequestDTO dto){
        return Product.builder()
                .name(dto.name())
                .description(dto.description())
                .unit_Price(dto.unit_Price())
                .stock_quantity(dto.stock_quantity())
                .active(dto.active())
                .build();
    }

    public ProductResponseDTO toResponse(Product entity){
        return new ProductResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getUnit_Price(),
                entity.getStock_quantity(),
                entity.getActive()
        );
    }
}

