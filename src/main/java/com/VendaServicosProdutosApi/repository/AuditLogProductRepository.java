package com.VendaServicosProdutosApi.repository;

import com.VendaServicosProdutosApi.model.AuditLogProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogProductRepository extends JpaRepository<AuditLogProduct, Long> {
}
