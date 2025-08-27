package com.VendaServicosProdutosApi.repository;


import com.VendaServicosProdutosApi.model.PrintService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrintServiceRepository extends JpaRepository<PrintService, Long> {
}
