package com.VendaServicosProdutosApi.VendaServicosProdutosApi.repository;

import com.VendaServicosProdutosApi.VendaServicosProdutosApi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
