package com.VendaServicosProdutosApi.controller;

import com.VendaServicosProdutosApi.dto.DailyReportDTO;
import com.VendaServicosProdutosApi.dto.SalesReportDTO;
import com.VendaServicosProdutosApi.exception.AuthenticationException;
import com.VendaServicosProdutosApi.exception.EstoqueInsuficienteException;
import com.VendaServicosProdutosApi.exception.RecursoNaoEncontradoException;
import com.VendaServicosProdutosApi.model.SalesOrder;
import com.VendaServicosProdutosApi.service.SalesOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    public ResponseEntity<?> salesOrderSave(@RequestBody SalesOrder salesOrder) {
        try {
            SalesOrder savedSalesOrder = salesOrderService.salesOrderSave(salesOrder);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedSalesOrder);
        } catch (EstoqueInsuficienteException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> salesOrderUpdate(@PathVariable Long id, @RequestBody SalesOrder salesOrder) {
        try {
            SalesOrder savedSalesOrder = salesOrderService.salesOrderUpdate(id, salesOrder);
            return ResponseEntity.ok(savedSalesOrder);
        } catch (EstoqueInsuficienteException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}/StatusOrder")
    public ResponseEntity<SalesOrder> salesOrderStatusUpdate(@PathVariable Long id, @RequestBody SalesOrder salesOrder){
        try {
            SalesOrder statusOrderUpdated = salesOrderService.salesOrderStatusUpdate(id, salesOrder);
            return ResponseEntity.ok(statusOrderUpdated);
        } catch (RecursoNaoEncontradoException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
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

    @GetMapping("/reportSaler")
    public List<SalesReportDTO> getSalesReport() {
        return salesOrderService.getSalesReport();
    }

    @GetMapping("/yesterdayRevenue")
    public BigDecimal getYesterdayRevenue() {
        return salesOrderService.getYesterdayRevenue();
    }

    @GetMapping("/reportByDate")
    public ResponseEntity<DailyReportDTO> getReportByDate(@RequestParam("date") LocalDate date) {
        try {
            return ResponseEntity.ok(salesOrderService.getReportByDate(date));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
