package com.VendaServicosProdutosApi.service;

import com.VendaServicosProdutosApi.dto.DailyReportDTO;
import com.VendaServicosProdutosApi.dto.OrderItensDTO;
import com.VendaServicosProdutosApi.dto.PrintServiceDTO;
import com.VendaServicosProdutosApi.dto.ProductItemDTO;
import com.VendaServicosProdutosApi.dto.SalesReportDTO;
import com.VendaServicosProdutosApi.exception.AuthenticationException;
import com.VendaServicosProdutosApi.exception.EstoqueInsuficienteException;
import com.VendaServicosProdutosApi.exception.RecursoNaoEncontradoException;
import com.VendaServicosProdutosApi.model.*;
import com.VendaServicosProdutosApi.repository.PrintServiceRepository;
import com.VendaServicosProdutosApi.repository.ProductRepository;
import com.VendaServicosProdutosApi.repository.SalesOrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SalesOrderService {

    private final SalesOrderRepository salesOrderRepository;
    private final ProductRepository productRepository;
    private final PrintServiceRepository printServiceRepository;
    private final OrderItensService orderItensService;
    private final AuthService authService;
    private final EstoqueNotificacaoService estoqueNotificacaoService;


    public List<SalesOrder> getAllSalesOrders() {
        return salesOrderRepository.findAll();
    }

    @Transactional
    public SalesOrder salesOrderSave(SalesOrder salesOrder) {
        updateList(salesOrder);
        salesOrder.setStatus(OrderStatus.PAGO);
        return salesOrderRepository.save(salesOrder);
    }


    public void updateList(SalesOrder salesOrder) {
        for (OrderItens item : salesOrder.getOrderItensList()) {
            item.setSalesOrder(salesOrder);

            // Verifica se é Produto
            if ("PRODUTO".equalsIgnoreCase(item.getItemType().toString()) && item.getProduct().getId() != null) {
                Product product = productRepository.findById(item.getProduct().getId())
                        .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado"));

                if (item.getQuantity() > product.getStock_quantity()) {
                    throw new EstoqueInsuficienteException(
                            "Estoque insuficiente para o produto '" + product.getName() +
                            "'. Disponível: " + product.getStock_quantity() +
                            ", solicitado: " + item.getQuantity()
                    );
                }

                product.setStock_quantity(product.getStock_quantity() - item.getQuantity());
                productRepository.save(product);
                estoqueNotificacaoService.verificarEstoqueMinimo(product);

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

    @Transactional
    public SalesOrder salesOrderUpdate(Long id, SalesOrder salesOrder) {

        SalesOrder salesOrderDB = salesOrderRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado!"));

        if(salesOrderDB.getStatus() == OrderStatus.PAGO){
            throw new RecursoNaoEncontradoException("Pedido já está pago, não pode ser auterado!");
        }
        salesOrder.setId(id);
        updateList(salesOrder);
        salesOrder.setStatus(OrderStatus.PAGO);
        return salesOrderRepository.save(salesOrder);
    }

    public SalesOrder salesOrderStatusUpdate(Long id, SalesOrder salesOrder){
        SalesOrder statusOrderUpdated = salesOrderRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado!"));

        statusOrderUpdated.setStatus(salesOrder.getStatus());
        return salesOrderRepository.save(statusOrderUpdated);
    }

    public void salesOrderDelete(Long idSalesOrder) {
        salesOrderRepository.findById(idSalesOrder).orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado " + idSalesOrder));
        salesOrderRepository.deleteById(idSalesOrder);
    }

    public List<SalesReportDTO> getSalesReport() {

        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        List<Object[]> rows = salesOrderRepository.findSalesReport(user.getId());

        Map<Long, SalesReportDTO> map = new LinkedHashMap<>();

        for (Object[] row : rows) {

            Long orderId = (Long) row[0];

            SalesReportDTO report = map.computeIfAbsent(orderId, id -> {

                SalesReportDTO dto = new SalesReportDTO();
                dto.setOrderId(id);
                dto.setCustomer((String) row[1]);
                dto.setDateHour((java.time.LocalDateTime) row[2]);
                dto.setTotalValueOrder((java.math.BigDecimal) row[3]);
                dto.setOrderItens(new ArrayList<>());

                return dto;
            });

            OrderItensDTO orderItens = new OrderItensDTO();
            orderItens.setName((String) row[4]);
            orderItens.setId((Long) row[5]);
            orderItens.setQuantity((Integer) row[6]);
            orderItens.setPrice((BigDecimal) row[7]);
            orderItens.setItemType((ItemType) row[8]);

            report.getOrderItens().add(orderItens);

        }

        return new ArrayList<>(map.values());
    }

    public BigDecimal getYesterdayRevenue() {

        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        LocalDate yesterday = LocalDate.now().minusDays(1);

        LocalDateTime start = yesterday.atStartOfDay();
        LocalDateTime end = yesterday.plusDays(1).atStartOfDay();

        return salesOrderRepository
                .findRevenueBetweenDates(user.getId(), start, end);
    }

    public DailyReportDTO getReportByDate(LocalDate date) {

        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (user.getUsertype() != UserType.ADMIN) {
            throw new AuthenticationException("Acesso negado: apenas administradores podem acessar este relatório.");
        }

        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();

        List<Object[]> rows = salesOrderRepository.findSalesReportByDate(start, end);

        Map<Long, SalesReportDTO> map = new LinkedHashMap<>();

        for (Object[] row : rows) {

            Long orderId = (Long) row[0];

            SalesReportDTO report = map.computeIfAbsent(orderId, id -> {
                SalesReportDTO dto = new SalesReportDTO();
                dto.setOrderId(id);
                dto.setCustomer((String) row[1]);
                dto.setDateHour((LocalDateTime) row[2]);
                dto.setTotalValueOrder((BigDecimal) row[3]);
                dto.setOrderItens(new ArrayList<>());
                return dto;
            });

            OrderItensDTO orderItens = new OrderItensDTO();
            orderItens.setName((String) row[4]);
            orderItens.setId((Long) row[5]);
            orderItens.setQuantity((Integer) row[6]);
            orderItens.setPrice((BigDecimal) row[7]);
            orderItens.setItemType((ItemType) row[8]);

            report.getOrderItens().add(orderItens);
        }

        List<SalesReportDTO> orders = new ArrayList<>(map.values());

        BigDecimal totalRevenue = orders.stream()
                .map(SalesReportDTO::getTotalValueOrder)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        DailyReportDTO dailyReport = new DailyReportDTO();
        dailyReport.setDate(date);
        dailyReport.setTotalRevenue(totalRevenue);
        dailyReport.setOrders(orders);

        return dailyReport;
    }

}
