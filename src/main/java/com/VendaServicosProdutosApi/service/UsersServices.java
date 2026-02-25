package com.VendaServicosProdutosApi.service;


import com.VendaServicosProdutosApi.dto.requestDTO.UserCreateRequestDTO;
import com.VendaServicosProdutosApi.dto.responseDTO.UserResponseDTO;
import com.VendaServicosProdutosApi.exception.RecursoNaoEncontradoException;
import com.VendaServicosProdutosApi.mapper.UserMapper;
import com.VendaServicosProdutosApi.model.User;
import com.VendaServicosProdutosApi.repository.TokenRepository;
import com.VendaServicosProdutosApi.repository.UsersRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @Autowired
    private TokenRepository tokenRepository;

    public List<User> getAllUsers(){
        return usersRepository.findAll();
    }

    public List<User> getAllUsersTrue() {
        return usersRepository.findByActiveTrue();
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
        if(userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
            userFromDb.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }
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

    @Transactional
    public void deleteUserById(Long userId) {
       User user = usersRepository.findById(userId).orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado: " + userId));
//        usersRepository.deleteById(idUser);
        user.setActive(false);
    }

//    public void activateUser(Long userId){
//        User userToActivate = usersRepository.findByActiveFalse(userId)
//    }

}
