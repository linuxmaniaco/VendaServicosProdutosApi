package com.VendaServicosProdutosApi.service;


import com.VendaServicosProdutosApi.dto.AuthUserDTO;
import com.VendaServicosProdutosApi.exception.AuthenticationException;
import com.VendaServicosProdutosApi.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class SecurityService {
//    private final UsersServices usersServices;
////    private final TokenService tokenService;
//    public String authenticate(AuthUserDTO authUserDTO) throws AuthenticationException {
//        Optional<User> byEmail = usersServices. findByEmail(authUserDTO.email());
//        if(byEmail.isEmpty()) throw new AuthenticationException("Usuario ou senha incorretos");
//        User usuario = byEmail.get();
//        if(usuario.getPassword().equals(authUserDTO.password())) return tokenService.generateToken(usuario);
//        else throw new AuthenticationException("Usuario ou senha incorretos");
//    }
}
