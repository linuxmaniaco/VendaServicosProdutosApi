package com.VendaServicosProdutosApi.controller;

import com.VendaServicosProdutosApi.exception.RecursoNaoEncontradoException;
import com.VendaServicosProdutosApi.model.ServiceVariation;
import com.VendaServicosProdutosApi.service.ServiceVariationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/serviceVariation")
@RequiredArgsConstructor
@Slf4j
public class ServiceVariationController {
    private final ServiceVariationService serviceVariationService;

    @GetMapping
    public List<ServiceVariation> getAllVariationTypes() {
        return serviceVariationService.getAllVariationTypes();
    }

    @GetMapping("/active")
    public Optional<ServiceVariation> getActiveVariationTypes() {
        return serviceVariationService.getActiveVariationTypes();
    }

    @PostMapping
    public ResponseEntity<ServiceVariation> createVariationType(@RequestBody @Valid ServiceVariation variationType) {
        ServiceVariation created = serviceVariationService.createVariationType(variationType);
        return ResponseEntity.created(URI.create("/api/variation-types/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceVariation> updateVariationType(@PathVariable Long id, @RequestBody ServiceVariation variationType) {
        try {
            serviceVariationService.updateVariationType(id, variationType);
            return ResponseEntity.ok().body(variationType);
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVariationType(@PathVariable Long id) {
        try {
            serviceVariationService.deleteVariationType(id);
            return ResponseEntity.ok().build();
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
