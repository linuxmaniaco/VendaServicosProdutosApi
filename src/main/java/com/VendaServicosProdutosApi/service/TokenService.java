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

//    private final String secret = "MY-SUPER-SECRET-1234";

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

//
//    public String generateToken(User user) {
//        Algorithm algorithm = Algorithm.HMAC512(secret);
//        return JWT.create()
//                .withIssuer("ACME.COM")
//                .withSubject(String.valueOf(user.getId()))
//                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
//                .withIssuedAt(LocalDateTime.now().toInstant(ZoneOffset.UTC))
//                .withClaim("email", user.getEmail())
//                .sign(algorithm);
//    }
//
//    public DecodedJWT isValid(String token) {
//        DecodedJWT decodedJWT;
//        String valid = startsWithBearer(token);
//        Algorithm algorithm = Algorithm.HMAC512(secret);
//        JWTVerifier verifier = JWT.require(algorithm)
//                .withIssuer("ACME.COM")
//                .build();
//        decodedJWT = verifier.verify(valid);
//        return decodedJWT;
//    }
//
//    public String getUsuario(DecodedJWT decodedJWT) {
//        return decodedJWT.getSubject();
//    }
//
//    private String startsWithBearer(String token){
//        if(!token.startsWith("Bearer")){
//            throw new IllegalArgumentException("Invalid Token") ;
//        }
//        return token.replace("Bearer", "").trim();
//    }

}
