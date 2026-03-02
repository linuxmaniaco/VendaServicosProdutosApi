package com.VendaServicosProdutosApi.repository;


import com.VendaServicosProdutosApi.model.PrintService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrintServiceRepository extends JpaRepository<PrintService, Long> {
    boolean existsPrintServiceByName(String name);
    List<PrintService> findByActiveTrue();
}
