package com.VendaServicosProdutosApi.service;

import com.VendaServicosProdutosApi.exception.RecursoNaoEncontradoException;
import com.VendaServicosProdutosApi.model.SalesOrder;
import com.VendaServicosProdutosApi.repository.SalesOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesOrderService {
    private final SalesOrderRepository salesOrderRepository;

    public List<SalesOrder> getAllSalesOrders() {
        return salesOrderRepository.findAll();
    }

    public SalesOrder salesOrderSave(SalesOrder salesOrder) {
        return salesOrderRepository.save(salesOrder);
    }

    public SalesOrder salesOrderUpdate(Long id, SalesOrder salesOrder) {
        if(!salesOrderRepository.findById(id).isPresent()) {
            throw new RecursoNaoEncontradoException("Cliente não encontrado: " + id);
        }
        salesOrder.setId(id);
        return salesOrderRepository.save(salesOrder);
    }

    public void salesOrderDelete(Long idSalesOrder) {
        salesOrderRepository.findById(idSalesOrder).orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado " + idSalesOrder));
        salesOrderRepository.deleteById(idSalesOrder);
    }

}
