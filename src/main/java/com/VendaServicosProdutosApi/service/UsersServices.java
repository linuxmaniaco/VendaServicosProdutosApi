package com.VendaServicosProdutosApi.service;


import com.VendaServicosProdutosApi.exception.RecursoNaoEncontradoException;
import com.VendaServicosProdutosApi.model.User;
import com.VendaServicosProdutosApi.repository.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersServices {
    private final UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    public User saveUser(User user) {
        User encryptedUserPassword = new User();

        encryptedUserPassword.setName(user.getName());
        encryptedUserPassword.setLogin(user.getLogin());
        encryptedUserPassword.setPassword(passwordEncoder.encode(user.getPassword()));
        encryptedUserPassword.setEmail(user.getEmail());
        encryptedUserPassword.setUsertype(user.getUsertype());
        encryptedUserPassword.setAvatar(user.getAvatar());
        encryptedUserPassword.setActive(user.getActive());

        return usersRepository.save(encryptedUserPassword);
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

    public Optional<Optional<User>> findUserByEmail(String email) {
        Optional<User> byEmail = usersRepository.findByEmail(email);
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
