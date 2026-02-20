package com.VendaServicosProdutosApi.controller;

import com.VendaServicosProdutosApi.dto.AuthUserDTO;
import com.VendaServicosProdutosApi.service.AuthService;
import com.VendaServicosProdutosApi.service.TokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Gerenciador de token")
public class AuthController {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthService authService;

    @PostMapping("/generateToken")
    public ResponseEntity<?> gerarToken(@RequestBody AuthUserDTO request) {
        try {

            var response = authService.authenticate(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Usuário ou senha inválida!");
        }
    }
}
