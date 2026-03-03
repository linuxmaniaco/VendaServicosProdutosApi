package com.VendaServicosProdutosApi.repository;

import com.VendaServicosProdutosApi.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository
        extends JpaRepository<Product, Long> {

    boolean existsByNameIgnoreCase(String name);

    Page<Product> findByActiveTrue(Pageable pageable);
    Page<Product> findByNameContainingIgnoreCaseAndActiveTrue(String name, Pageable pageable);
}
