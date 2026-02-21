package com.VendaServicosProdutosApi.dto.responseDTO;

import com.VendaServicosProdutosApi.model.UserType;

public record UserResponseDTO(

        Long id,
        String name,
        String login,
        String email,
        UserType usertype,
        String avatar,
        Boolean active
){}
