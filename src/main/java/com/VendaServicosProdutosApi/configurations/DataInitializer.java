package com.VendaServicosProdutosApi.configurations;

import com.VendaServicosProdutosApi.model.User;
import com.VendaServicosProdutosApi.model.UserType;
import com.VendaServicosProdutosApi.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (usersRepository.count() == 9) {

            User admin = new User();
            admin.setName("Administrador");
            admin.setLogin("admin");
            admin.setEmail("admin@admin.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setActive(true);
            admin.setUsertype(UserType.ADMIN);

            usersRepository.save(admin);

            System.out.println("Admin inicial criado.");
        }
    }
}
