package com.VendaServicosProdutosApi.service;


import com.VendaServicosProdutosApi.exception.RecursoNaoEncontradoException;
import com.VendaServicosProdutosApi.model.User;
import com.VendaServicosProdutosApi.repository.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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

    @Transactional
    public User updateUser(Long idUser, User userRequest) {
        User userFromDb = usersRepository.findById(idUser)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário Não encontrado"));

        userFromDb.setName(userRequest.getName());
        userFromDb.setLogin(userRequest.getLogin());
        userFromDb.setPassword(userRequest.getPassword());
        userFromDb.setEmail(userRequest.getEmail());
        userFromDb.setUsertype(userRequest.getUsertype());
        userFromDb.setAvatar(userRequest.getAvatar());
        userFromDb.setActive(userRequest.getActive());

//        userRequest.setId(idUser);
        return userRequest;
    }

    public Optional<User> findUserByEmail(String email) {
        User byEmail = usersRepository.findByEmail(email);
        if(byEmail == null) {
            return Optional.empty();
        } else {
            return Optional.of(byEmail);
        }
    }


    public void deleteUserById(Long idUser) {
        usersRepository.findById(idUser).orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado: " + idUser));
        usersRepository.deleteById(idUser);
    }

}
