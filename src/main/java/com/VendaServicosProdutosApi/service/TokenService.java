package com.VendaServicosProdutosApi.service;

import com.VendaServicosProdutosApi.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Slf4j
@Service
public class TokenService {
//    private final String secret = "MY-SUPER-SECRET-1234";
//    public String generateToken(User user) {
//        Algorithm algorithm = Algorithm.HMAC512(secret);
//        return JWT.create()
//                .withIssuer("ACME.COM")
//                .withSubject(user.getId().toString())
//                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
//                .withIssuedAt(LocalDateTime.now().toInstant(ZoneOffset.UTC))
//                .withClaim("email", user.getEmail())
//                .sign(algorithm);
//    }

}
