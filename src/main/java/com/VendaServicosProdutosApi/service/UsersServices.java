package com.VendaServicosProdutosApi.service;


import com.VendaServicosProdutosApi.exception.RecursoNaoEncontradoException;
import com.VendaServicosProdutosApi.model.User;
import com.VendaServicosProdutosApi.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersServices {
    private final UsersRepository usersRepository;

    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    public User saveUser(User user) {
        return usersRepository.save(user);
    }

    public User findUserById(Long idUser) {
        return usersRepository.findById(idUser)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado: " + idUser));
    }

    public User updateUser(Long idUser, User user) {
        if(!usersRepository.existsById(idUser)) {
            throw new RecursoNaoEncontradoException("Usuário não encontrado: " + idUser);
        }
        user.setId(idUser);
        return usersRepository.save(user);
    }


    public void deleteUserById(Long idUser) {
        usersRepository.findById(idUser).orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado: " + idUser));
        usersRepository.deleteById(idUser);
    }

}
