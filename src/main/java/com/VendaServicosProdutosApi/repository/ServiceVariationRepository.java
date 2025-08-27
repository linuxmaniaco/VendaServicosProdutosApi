package com.VendaServicosProdutosApi.repository;

import com.VendaServicosProdutosApi.model.ServiceVariation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceVariationRepository extends JpaRepository<ServiceVariation, Long> {
    Optional<ServiceVariation> findByActive(Boolean active);
}
