package com.VendaServicosProdutosApi.mapper;

import com.VendaServicosProdutosApi.dto.requestDTO.PrintServiceCreateRequestDTO;
import com.VendaServicosProdutosApi.dto.responseDTO.PrintServiceResponseDTO;
import com.VendaServicosProdutosApi.model.PrintService;
import org.springframework.stereotype.Component;

@Component
public class PrintServiceMapper {

    public PrintService toEntity(PrintServiceCreateRequestDTO dto){
        return PrintService.builder()
                .name(dto.name())
                .description(dto.description())
                .price(dto.price())
                .active(dto.active())
                .build();
    }

    public PrintServiceResponseDTO toResponse(PrintService entity){
        return new PrintServiceResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.isActive()
        );
    }
}