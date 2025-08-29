package com.VendaServicosProdutosApi.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sales_order")
public class SalesOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_Hour", nullable = false, updatable = false)
    private LocalDateTime dataHour = LocalDateTime.now();

    @Column(name = "customer", nullable = false, length = 255)
    private String customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @Column(name = "total_Value_Order", nullable = false)
    private BigDecimal totalValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus status = OrderStatus.ABERTO;
}

