package com.VendaServicosProdutosApi.repository;

import com.VendaServicosProdutosApi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Boolean existsByEmailIgnoreCase(String email);
    List<User> findByActiveTrue();
    List<User> findByActiveFalse();
}
