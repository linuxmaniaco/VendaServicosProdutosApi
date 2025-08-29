package com.VendaServicosProdutosApi.repository;

import com.VendaServicosProdutosApi.model.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {
}
