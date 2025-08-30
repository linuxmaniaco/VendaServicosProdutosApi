package com.VendaServicosProdutosApi.model;

import jakarta.persistence.*;
import jakarta.persistence.criteria.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "order_itens")
public class OrderItens {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_value_at_time_of_sale", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitValueAtTimeOfSale;

    @Column(name = "valor_total_item", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalItemValue;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_sales_order", nullable = false)
    private SalesOrder salesOrder;

    // 🔹 Associação com Produto (opcional)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_product")
    private Product product;

    // 🔹 Associação com Serviço (opcional)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_print_service")
    private PrintService printService;

    @Enumerated(EnumType.STRING)
    @Column(name = "id_item_type", nullable = false)
    private ItemType itemType;

    // 🔹 Regra de consistência: calcula o total automaticamente
    @PrePersist
    @PreUpdate
    public void calculateTotalValue() {
        if (quantity != null && unitValueAtTimeOfSale != null) {
            this.totalItemValue = unitValueAtTimeOfSale.multiply(BigDecimal.valueOf(quantity));
        }
    }
}
