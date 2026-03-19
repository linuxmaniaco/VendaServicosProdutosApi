package com.VendaServicosProdutosApi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "logs_impressao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogsImpressao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "data")
    private LocalDate data;

    @Column(name = "hora")
    private LocalTime hora;

    @Column(name = "usuario", length = 100)
    private String usuario;

    @Column(name = "paginas")
    private Integer paginas;

    @Column(name = "copias")
    private Integer copias;

    @Column(name = "fila_impressao", length = 255)
    private String filaImpressao;

    @Column(name = "documento", columnDefinition = "TEXT")
    private String documento;

    @Column(name = "estacao", length = 100)
    private String estacao;

    @Column(name = "duplex", length = 10)
    private String duplex;

    @Column(name = "escala_cinza", length = 10)
    private String escalaCinza;
}
