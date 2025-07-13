package com.VendaServicosProdutosApi.VendaServicosProdutosApi.controller;

import com.VendaServicosProdutosApi.VendaServicosProdutosApi.model.User;
import com.VendaServicosProdutosApi.VendaServicosProdutosApi.service.UsersServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
@Slf4j
public class UsersController {

    private final UsersServices usersServices;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> user = usersServices.getAllUsers();
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = usersServices.saveUser(user); // Aqui @PrePersist é chamado
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@RequestParam Long idUser) {
        try {
            User user = usersServices.findUserById(idUser);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable Long id) {
        try {
            User userUpdated = usersServices.updateUser(id, user);
            return ResponseEntity.ok(userUpdated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
