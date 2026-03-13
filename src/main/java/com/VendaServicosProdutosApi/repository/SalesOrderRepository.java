package com.VendaServicosProdutosApi.repository;

import com.VendaServicosProdutosApi.model.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.converter.json.GsonBuilderUtils;

import java.util.List;


public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {
    @Query("""
        SELECT 
            so.id,
            so.customer,
            so.dataHour,
            so.totalValue,
            COALESCE(p.name, s.name) AS itemName,
            oi.id,
            oi.quantity,
            oi.unitValueAtTimeOfSale,
            oi.itemType
        FROM OrderItens oi
        JOIN oi.salesOrder so
        LEFT JOIN oi.product p
        LEFT JOIN oi.printService s
        WHERE DATE(so.dataHour) = CURRENT_DATE AND so.user.id = :userId
        ORDER BY so.dataHour DESC
    """)
    List<Object[]> findSalesReport(Long userId);
}

