package com.VendaServicosProdutosApi.service;

import com.VendaServicosProdutosApi.exception.RecursoNaoEncontradoException;
import com.VendaServicosProdutosApi.model.OrderItens;
import com.VendaServicosProdutosApi.model.PrintService;
import com.VendaServicosProdutosApi.model.Product;
import com.VendaServicosProdutosApi.model.SalesOrder;
import com.VendaServicosProdutosApi.repository.OrderItensRepository;
import com.VendaServicosProdutosApi.repository.PrintServiceRepository;
import com.VendaServicosProdutosApi.repository.ProductRepository;
import com.VendaServicosProdutosApi.repository.SalesOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SalesOrderService {

    private final SalesOrderRepository salesOrderRepository;
    private final ProductRepository  productRepository;
    private final PrintServiceRepository  printServiceRepository;

    public List<SalesOrder> getAllSalesOrders() {
        return salesOrderRepository.findAll();
    }

    public SalesOrder salesOrderSave(SalesOrder salesOrder) {

        for (OrderItens item : salesOrder.getOrderItensList()) {

            item.setSalesOrder(salesOrder);

            // Verifica se é Produto
            if ("PRODUTO".equalsIgnoreCase(item.getItemType().name())) {
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

        return salesOrderRepository.save(salesOrder);

//            // Processa e garante o vínculo entre SalesOrder e OrderItens
//            for (OrderItens item : salesOrder.getOrderItensList()) {
//                item.setSalesOrder(salesOrder);  // Relaciona o item com o pedido
//                item.calculateTotalValue();  // Calcula o valor total do item
//            }
//
//        // Calcula o valor total do pedido somando todos os itens
//        BigDecimal totalValue = salesOrder.getOrderItensList()
//                .stream()
//                .map(OrderItens::getTotalItemValue)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//        salesOrder.setTotalValue(totalValue); // Atualiza o total do pedido
//
//        return salesOrderRepository.save(salesOrder);
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
