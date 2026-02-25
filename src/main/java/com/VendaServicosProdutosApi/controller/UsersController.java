package com.VendaServicosProdutosApi.controller;

import com.VendaServicosProdutosApi.dto.requestDTO.UserCreateRequestDTO;
import com.VendaServicosProdutosApi.dto.responseDTO.UserResponseDTO;
import com.VendaServicosProdutosApi.exception.RecursoNaoEncontradoException;
import com.VendaServicosProdutosApi.model.User;
import com.VendaServicosProdutosApi.model.UserType;
import com.VendaServicosProdutosApi.service.UsersServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class UsersController {

    private final UsersServices usersServices;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> user = usersServices.getAllUsers();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/true")
    public ResponseEntity<List<User>> getAllUsersTrue() {
        List<User> user = usersServices.getAllUsersTrue();
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserCreateRequestDTO request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usersServices.saveUser(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) {
        try {
            User user = usersServices.findUserById(id);
            return ResponseEntity.ok(user);
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        try {
            usersServices.deleteUserById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @GetMapping("/user-types")
    public List<UserType> getUserTypes() {
        return Arrays.asList(UserType.values());
    }

}
