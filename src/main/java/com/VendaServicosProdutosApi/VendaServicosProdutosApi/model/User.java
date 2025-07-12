package com.VendaServicosProdutosApi.VendaServicosProdutosApi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User extends DomainBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false, unique = true)
    private String login;

    @NotNull
    @Column(nullable = false)
    private String password;

    @NotNull
    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private UserType usertype;

    private String avatar;

    @NotNull
    @Column(nullable = false)
    private Boolean active;
}


//package com.VendaServicosProdutosApi.VendaServicosProdutosApi.model;
//
//import jakarta.annotation.Nullable;
//import jakarta.persistence.*;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import lombok.*;
//import org.antlr.v4.runtime.misc.NotNull;
//
//@EqualsAndHashCode(callSuper = true)
//@Entity
//@Data@AllArgsConstructor@NoArgsConstructor@Builder
//public class Users extends DomainBase {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//
//    @NotNull
//    @Column(nullable = false)
//    private String name;
//
//    @NotNull
//    @Column(nullable = false, unique = true)
//    private String login;
//
//    @NotNull
//    @Column(nullable = false)
//    private String password;
//
//
//    @NotNull
//    @Column(nullable = false, unique = true)
//    private String email;
//
//    @Enumerated(EnumType.STRING)
//    private UserType usertype;
//
//    private String avatar;
//
//    @NotNull
//    @Column(nullable = false)
//    private Boolean active;
//
//}
