package com.VendaServicosProdutosApi.dto.requestDTO;

import com.VendaServicosProdutosApi.model.UserType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserCreateRequestDTO(

        @NotBlank(message = "Nome obrigatorio")
        String name,

        @NotBlank(message = "Login obrigatorio")
        String login,

        @NotBlank(message = "Senha Obrigatoria")
        String password,

        @NotBlank(message = "Email Obrigatório")
        String email,

        @NotNull
        UserType usertype,

        @NotNull
        String avatar,

        @NotNull
        Boolean active
) {}
