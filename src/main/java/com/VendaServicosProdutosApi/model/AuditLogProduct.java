package com.VendaServicosProdutosApi.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "audit_log_product")
public class AuditLogProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String product; // Ex: "Produto"
    private Long productId;
    private String actionType; // INSERT, UPDATE, DELETE

    @Lob
    private String oldData;

    @Lob
    private String newData;

    private Long userId; // id do usuário
    private String reason;

    private LocalDateTime performedAt = LocalDateTime.now();
}
