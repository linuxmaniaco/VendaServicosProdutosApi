package com.VendaServicosProdutosApi.controller;

import com.VendaServicosProdutosApi.exception.RecursoNaoEncontradoException;
import com.VendaServicosProdutosApi.model.Product;
import com.VendaServicosProdutosApi.model.SalesOrder;
import com.VendaServicosProdutosApi.model.ServiceVariation;
import com.VendaServicosProdutosApi.service.SalesOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
public class SalesOrderController {

    private final SalesOrderService salesOrderService;

    @GetMapping
    public ResponseEntity<List<SalesOrder>> findAll() {
        List<SalesOrder> salesOrders = salesOrderService.getAllSalesOrders();
        return ResponseEntity.ok(salesOrders);
    }

    @PostMapping
    public ResponseEntity<SalesOrder> salesOrderSave(@RequestBody SalesOrder salesOrder) {
        SalesOrder savedSalesOrder = salesOrderService.salesOrderSave(salesOrder);
        return ResponseEntity.ok(savedSalesOrder);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalesOrder> salesOrderUpdate(@PathVariable Long id, @RequestBody SalesOrder salesOrder) {
        try {
            SalesOrder savedSalesOrder = salesOrderService.salesOrderUpdate(id, salesOrder);
            return ResponseEntity.ok(savedSalesOrder);
        } catch (RuntimeException e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> salesOrderDelete(@PathVariable Long id) {
        try {
            salesOrderService.salesOrderDelete(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


}
