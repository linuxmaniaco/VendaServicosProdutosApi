package com.VendaServicosProdutosApi.mapper;

import com.VendaServicosProdutosApi.dto.ProductSummaryResponseDTO;
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
                .minimumStock(dto.minimumStock())
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
                entity.getMinimumStock(),
                entity.getActive()
        );
    }

    public ProductSummaryResponseDTO toSummaryResponse(Product product){
        return new ProductSummaryResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getUnit_Price(),
                product.getStock_quantity(),
                product.getMinimumStock(),
                product.getActive()

        );
    }
}

