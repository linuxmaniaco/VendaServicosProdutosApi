package com.VendaServicosProdutosApi.dto.requestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PrintServiceCreateRequestDTO(

        @NotBlank(message = "Nome é obrigatório")
        String name,

        @NotBlank(message = "Descrição é obrigatório")
        String description,

        @NotNull
        @Positive
        Double price,

        @NotNull
        Boolean active
){}