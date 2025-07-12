package com.VendaServicosProdutosApi.VendaServicosProdutosApi.service;

import com.VendaServicosProdutosApi.VendaServicosProdutosApi.model.User;
import com.VendaServicosProdutosApi.VendaServicosProdutosApi.repository.UsersRepository;
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

    public Optional<User> findUserById(Long idUser) {
        Optional<User> userById = usersRepository.findById(idUser);
        if(userById.isPresent()){
            User user = userById.get();
            user.setPassword(null);
            return Optional.of((User) user);
        }
        return userById;
    }

}
