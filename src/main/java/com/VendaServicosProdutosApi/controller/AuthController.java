package com.VendaServicosProdutosApi.controller;

import com.VendaServicosProdutosApi.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Gerenciador de token")
public class AuthController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/generateToken")
    @Operation(summary = "generateToken", description = "Rota possível por gerar token!")
    public ResponseEntity<?> gerarToken(String email, String senha){
        try {
            if(!(email.equals("string") && senha.equals("string"))){
                return ResponseEntity.notFound().build();
            }
            var generatedToken = tokenService.generateToken(email);
            return ResponseEntity.ok(generatedToken);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
