package com.VendaServicosProdutosApi.repository;

import com.VendaServicosProdutosApi.model.OrderItens;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItensRepository extends JpaRepository<OrderItens, Long> {
}
