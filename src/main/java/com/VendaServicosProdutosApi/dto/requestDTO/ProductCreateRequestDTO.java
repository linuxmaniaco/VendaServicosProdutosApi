package com.VendaServicosProdutosApi.dto.requestDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductCreateRequestDTO(

    @NotBlank(message = "Name is required")
    String name,

    @NotBlank(message = "Name is required")
    String description,

    @NotNull
    @Positive
    Double unit_Price,

    @NotNull
    @Min(0)
    int stock_quantity,

    @NotNull
    @Min(0)
    Integer minimumStock,

    @NotNull
    Boolean active
){}
