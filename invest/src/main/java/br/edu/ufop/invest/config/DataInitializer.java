package br.edu.ufop.invest.config;

import br.edu.ufop.invest.entity.UserEntity;
import br.edu.ufop.invest.enums.RoleType;
import br.edu.ufop.invest.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String adminEmail = "admin@admin.com";
            UserEntity admin = userRepository.findByEmail(adminEmail).orElse(null);

            if (admin != null) {
                System.out.println("Usuário ADMIN já existe: " + admin.getEmail());
                return;
            }
            else {
                UserEntity newAdmin = new UserEntity();
                newAdmin.setName("Administrador");
                newAdmin.setEmail(adminEmail);
                newAdmin.setPassword(passwordEncoder.encode("admin123")); 
                newAdmin.setRole(RoleType.ADMIN);
                newAdmin.setActive(true);
                userRepository.save(newAdmin);
                System.out.println("Usuário ADMIN criado com sucesso!");
            }
        };
    }
}