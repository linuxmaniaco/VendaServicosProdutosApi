package com.VendaServicosProdutosApi.controller;

import com.VendaServicosProdutosApi.dto.OrderItensResponseDTO;
import com.VendaServicosProdutosApi.exception.RecursoNaoEncontradoException;
import com.VendaServicosProdutosApi.model.OrderItens;
import com.VendaServicosProdutosApi.service.OrderItensService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orderItem")
@RequiredArgsConstructor
@Slf4j
public class OrderItensController {

    private final OrderItensService orderItensService;

    @GetMapping
    public List<OrderItensResponseDTO> list(){
        return orderItensService.findAll();
    }

    @PostMapping
    public ResponseEntity<OrderItens> orderItensSave(@RequestBody OrderItens orderItens){
        OrderItens orderItensSave = orderItensService.orderItensSave(orderItens);
        return ResponseEntity.ok(orderItensSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItens> orderItensUpdate(
            @PathVariable Long id,
            @RequestBody OrderItens orderItens
    ){
        try {
            OrderItens updateOrderItens =  orderItensService.OrderItensUpdate(id, orderItens);
            return ResponseEntity.ok(updateOrderItens);
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> orderItensDelete(@PathVariable Long id){
        try {
            orderItensService.delete(id);
            return ResponseEntity.ok().build();
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
