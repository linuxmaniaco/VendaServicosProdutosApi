package com.VendaServicosProdutosApi.service;

import com.VendaServicosProdutosApi.exception.RecursoNaoEncontradoException;
import com.VendaServicosProdutosApi.model.OrderItens;
import com.VendaServicosProdutosApi.model.PrintService;
import com.VendaServicosProdutosApi.model.Product;
import com.VendaServicosProdutosApi.model.SalesOrder;
import com.VendaServicosProdutosApi.repository.PrintServiceRepository;
import com.VendaServicosProdutosApi.repository.ProductRepository;
import com.VendaServicosProdutosApi.repository.SalesOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesOrderService {

    private final SalesOrderRepository salesOrderRepository;
    private final ProductRepository  productRepository;
    private final PrintServiceRepository  printServiceRepository;
    private final OrderItensService orderItensService;

    public List<SalesOrder> getAllSalesOrders() {
        return salesOrderRepository.findAll();
    }

    public SalesOrder salesOrderSave(SalesOrder salesOrder) {

        updateList(salesOrder);
        return salesOrderRepository.save(salesOrder);
    }


    public void updateList(SalesOrder salesOrder) {
        for (OrderItens item : salesOrder.getOrderItensList()) {
            item.setSalesOrder(salesOrder);

            // Verifica se é Produto
            if ("PRODUTO".equalsIgnoreCase(item.getItemType().toString()) && item.getProduct().getId() != null) {
                Product product = productRepository.findById(item.getProduct().getId())
                        .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
                item.setUnitValueAtTimeOfSale(BigDecimal.valueOf(product.getUnit_Price()));
            }

            // Verifica se é Serviço
            if ("SERVICO".equalsIgnoreCase(item.getItemType().toString()) && item.getPrintService() != null) {
                PrintService service = printServiceRepository.findById(item.getPrintService().getId())
                        .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
                item.setUnitValueAtTimeOfSale(BigDecimal.valueOf(service.getPrice()));
            }

            // 🔹 Calcula o total do item automaticamente
            item.calculateTotalValue();

        }

        // 🔹 Calcula o total do pedido
        BigDecimal totalValue = salesOrder.getOrderItensList().stream()
                .map(OrderItens::getTotalItemValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        salesOrder.setTotalValue(totalValue);
    }

    public SalesOrder salesOrderUpdate(Long id, SalesOrder salesOrder) {
        if(!salesOrderRepository.findById(id).isPresent()) {
            throw new RecursoNaoEncontradoException("Cliente não encontrado: " + id);
        }
        salesOrder.setId(id);
        updateList(salesOrder);
        return salesOrderRepository.save(salesOrder);
    }

    public void salesOrderDelete(Long idSalesOrder) {
        salesOrderRepository.findById(idSalesOrder).orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado " + idSalesOrder));
        salesOrderRepository.deleteById(idSalesOrder);
    }

}
