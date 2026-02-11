package com.VendaServicosProdutosApi.controller;

import com.VendaServicosProdutosApi.dto.AuthUserDTO;
import com.VendaServicosProdutosApi.exception.AuthenticationException;
import com.VendaServicosProdutosApi.exception.RecursoNaoEncontradoException;
import com.VendaServicosProdutosApi.model.User;
import com.VendaServicosProdutosApi.payload.AuthPayload;
//import com.VendaServicosProdutosApi.service.SecurityService;
import com.VendaServicosProdutosApi.service.UsersServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class UsersController {

    private final UsersServices usersServices;
//    private final SecurityService securityService;


    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> user = usersServices.getAllUsers();
        return ResponseEntity.ok(user);
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> autenticate(@RequestBody AuthUserDTO authUserDTO){
//        try{
//            String authenticate = securityService.authenticate(authUserDTO);
//            return ResponseEntity.ok(new AuthPayload(authenticate));
//        }catch (AuthenticationException ex){
//            return ResponseEntity.status(HttpStatus.FORBIDDEN)
//                    .body(Map.of("message", "Usuario ou senha invalidos"));
//        }
//    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = usersServices.saveUser(user); // Aqui @PrePersist é chamado
        return ResponseEntity.ok(savedUser);
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

}
