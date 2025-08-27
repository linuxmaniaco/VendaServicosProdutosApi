package com.VendaServicosProdutosApi.service;


import com.VendaServicosProdutosApi.exception.RecursoNaoEncontradoException;
import com.VendaServicosProdutosApi.model.ServiceVariation;
import com.VendaServicosProdutosApi.repository.ServiceVariationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceVariationService {
    private final ServiceVariationRepository serviceVariationRepository;

    public List<ServiceVariation> getAllVariationTypes() {
        return serviceVariationRepository.findAll();
    }

    public Optional<ServiceVariation> getActiveVariationTypes() {
        return serviceVariationRepository.findByActive(true);
    }

    public ServiceVariation getVariationTypeById(Long id) {
        return serviceVariationRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Variação de serviço não encontrado: " + id));
    }

    public ServiceVariation createVariationType(ServiceVariation variationType) {
        return serviceVariationRepository.save(variationType);
    }

    public ServiceVariation updateVariationType(Long id,  ServiceVariation variationType) {
        if (!serviceVariationRepository.findById(id).isPresent()) {
            throw new RecursoNaoEncontradoException("Variação de Serviço não encontrado: " + id);
        }
        variationType.setId(id);
        return serviceVariationRepository.save(variationType);
    }

    public void deleteVariationType(Long id) {
        serviceVariationRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Variação de serviço não encontrado"));
    }

}
