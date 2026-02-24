package com.VendaServicosProdutosApi.dto;

import com.VendaServicosProdutosApi.model.UserType;

public record AuthResponse(String token, long id, String name, String login, String email, UserType usertype, String avatar, boolean active ) {}
//public class AuthResponse {
//    private String token;
//    private String email;
//
//    public AuthResponse(String token, String email) {
//        this.token = token;
//        this.email = email;
//    }
//
//    public String getToken() {
//        return token;
//    }
//
//    public void setToken(String token) {
//        this.token = token;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//}
