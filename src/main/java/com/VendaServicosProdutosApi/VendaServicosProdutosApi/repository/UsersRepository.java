package com.VendaServicosProdutosApi.VendaServicosProdutosApi.repository;

import com.VendaServicosProdutosApi.VendaServicosProdutosApi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, Long> {

}
