package com.VendaServicosProdutosApi.controller;

import com.VendaServicosProdutosApi.dto.requestDTO.PrintServiceCreateRequestDTO;
import com.VendaServicosProdutosApi.dto.responseDTO.PrintServiceResponseDTO;
import com.VendaServicosProdutosApi.dto.responseDTO.ProductResponseDTO;
import com.VendaServicosProdutosApi.exception.RecursoNaoEncontradoException;
import com.VendaServicosProdutosApi.mapper.PrintServiceMapper;
import com.VendaServicosProdutosApi.model.PrintService;
import com.VendaServicosProdutosApi.service.PrintServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service")
@RequiredArgsConstructor
@Slf4j
public class PrintServiceController {
    private final PrintServiceService printServiceService;

    @GetMapping
    public ResponseEntity<List<PrintService>> findAll() {
        List<PrintService> printServices = printServiceService.findAll();
        return ResponseEntity.ok(printServices);
    }

    @PostMapping
    public ResponseEntity<PrintServiceResponseDTO> CreateService(@Valid @RequestBody PrintServiceCreateRequestDTO request) {
       return ResponseEntity
               .status(HttpStatus.CREATED)
               .body(printServiceService.printServiceSave(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrintService> findServiceById(@PathVariable Long id) {
        try {
            PrintService printService = printServiceService.findPrintServiceById(id);
            return ResponseEntity.ok(printService);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrintService(@PathVariable Long id) {
        try {
            printServiceService.deletePrintService(id);
            return ResponseEntity.ok().build();
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrintService> updatePrintService(@PathVariable Long id, @RequestBody PrintService printService) {
        try {
            printServiceService.printServiceUpdate(id, printService);
            return ResponseEntity.ok(printService);
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
