package com.VendaServicosProdutosApi.repository;

import com.VendaServicosProdutosApi.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long>{
    boolean existsByAccessToken(String accessToken);
}
