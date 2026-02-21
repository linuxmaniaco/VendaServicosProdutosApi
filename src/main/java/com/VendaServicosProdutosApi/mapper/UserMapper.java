package com.VendaServicosProdutosApi.mapper;

import com.VendaServicosProdutosApi.dto.requestDTO.UserCreateRequestDTO;
import com.VendaServicosProdutosApi.dto.responseDTO.UserResponseDTO;
import com.VendaServicosProdutosApi.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toEntity(UserCreateRequestDTO dto){
        return User.builder()
                .name(dto.name())
                .login(dto.login())
                .password(dto.password())
                .email(dto.email())
                .usertype(dto.usertype())
                .avatar(dto.avatar())
                .active(dto.active())
                .build();
    }

    public UserResponseDTO toResponse(User entity){
        return new UserResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getLogin(),
                entity.getEmail(),
                entity.getUsertype(),
                entity.getAvatar(),
                entity.getActive()
        );

    }

}
