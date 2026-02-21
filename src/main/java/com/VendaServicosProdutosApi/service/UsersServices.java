package com.VendaServicosProdutosApi.service;


import com.VendaServicosProdutosApi.dto.requestDTO.UserCreateRequestDTO;
import com.VendaServicosProdutosApi.dto.responseDTO.UserResponseDTO;
import com.VendaServicosProdutosApi.exception.RecursoNaoEncontradoException;
import com.VendaServicosProdutosApi.mapper.UserMapper;
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
    private final UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    public UserResponseDTO saveUser(UserCreateRequestDTO request) {
        if(usersRepository.existsByEmailIgnoreCase(request.name()))
            throw new RecursoNaoEncontradoException("Usuário já existe");

        User user = userMapper.toEntity(request);


        user.setName(user.getName());
        user.setLogin(user.getLogin());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmail(user.getEmail());
        user.setUsertype(user.getUsertype());
        user.setAvatar(user.getAvatar());
        user.setActive(user.getActive());

        User savedUser = usersRepository.save(user);

        return userMapper.toResponse(savedUser);
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
