package com.VendaServicosProdutosApi.service;

import com.VendaServicosProdutosApi.model.Token;
import com.VendaServicosProdutosApi.repository.TokenRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class TokenService {

    @Value("${spring.secret}")
    private String secret;

    @Value("${spring.expiration}")
    private long expiration;

    @Value("${spring.emissor}")
    private String emissor;

    @Autowired
    private TokenRepository tokenRepository;

    public String generateToken(String subject){
        var dataExpiration = this.getDataExpiration();

        Algorithm algorithm = Algorithm.HMAC256(secret);
        String accessToken = JWT.create()
                .withIssuer(emissor)
                .withSubject(subject)
                .withExpiresAt(dataExpiration)
                .sign(algorithm);

        Token tokenEntity = new Token();
        tokenEntity.setAccessToken(accessToken);
        tokenRepository.save(tokenEntity);

        return accessToken;
    }

    public DecodedJWT verifyToken(String token) throws JWTVerificationException {
        if(!tokenRepository.existsByAccessToken(token)){
            throw new JWTVerificationException("Token inválido!");
        }

        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verify = JWT.require(algorithm).withIssuer(emissor).build();
        return verify.verify(token);
    }

    private Instant getDataExpiration(){
        return LocalDateTime.now().plusMinutes(expiration).toInstant(ZoneOffset.of("-03:00"));
    }
}
