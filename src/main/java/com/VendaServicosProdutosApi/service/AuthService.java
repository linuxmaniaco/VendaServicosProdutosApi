package com.VendaServicosProdutosApi.service;

import com.VendaServicosProdutosApi.dto.AuthResponse;
import com.VendaServicosProdutosApi.dto.AuthUserDTO;
import com.VendaServicosProdutosApi.exception.RecursoNaoEncontradoException;
import com.VendaServicosProdutosApi.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthResponse authenticate(AuthUserDTO request){

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String raw = "senha123";
        String newHash = encoder.encode(raw);

        System.out.println(newHash);
        System.out.println(encoder.matches(raw, newHash));


        var user = usersRepository.findByEmail(request.email())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado!"));

        if(!passwordEncoder.matches(request.password(), user.getPassword())){
            throw new RuntimeException("Senha Inválida!");
        }

        var token = tokenService.generateToken(user.getEmail());

        return new AuthResponse(token, user.getEmail());
    }
}
