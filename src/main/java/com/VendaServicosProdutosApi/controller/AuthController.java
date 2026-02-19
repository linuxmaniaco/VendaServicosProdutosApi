package com.VendaServicosProdutosApi.controller;

import com.VendaServicosProdutosApi.dto.AuthResponse;
import com.VendaServicosProdutosApi.dto.AuthUserDTO;
import com.VendaServicosProdutosApi.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
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

    @PostMapping("/generateToken")
    public ResponseEntity<?> gerarToken(@RequestBody AuthUserDTO request) {
        try {

            if (!(request.getEmail().equals("string@arena.com")
                    && request.getPassword().equals("string"))) {
                return ResponseEntity.status(401).build();
            }

            var generatedToken = tokenService.generateToken(request.getEmail());
            return ResponseEntity.ok(
                    new AuthResponse(generatedToken, request.getEmail())
            );

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
//    @PostMapping("/generateToken")
//    @Operation(summary = "generateToken", description = "Rota possível por gerar token!")
//    public ResponseEntity<?> gerarToken(String email, String password){
//        try {
//            if(!(email.equals("string@arena.com") && password.equals("string"))){
//                return ResponseEntity.notFound().build();
//            }
//            var generatedToken = tokenService.generateToken(email);
//            return ResponseEntity.ok(generatedToken);
//        } catch (Exception e){
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
}
