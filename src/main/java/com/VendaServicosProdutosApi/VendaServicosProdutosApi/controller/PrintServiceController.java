package com.VendaServicosProdutosApi.VendaServicosProdutosApi.controller;


import com.VendaServicosProdutosApi.VendaServicosProdutosApi.exception.RecursoNaoEncontradoException;
import com.VendaServicosProdutosApi.VendaServicosProdutosApi.model.PrintService;
import com.VendaServicosProdutosApi.VendaServicosProdutosApi.service.PrintServiceService;
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
    public ResponseEntity<PrintService> CreateService(@RequestBody PrintService printService) {
       PrintService printService1 = printServiceService.printServiceSave(printService);
       return ResponseEntity.ok(printService1);
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
