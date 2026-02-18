package br.edu.ufop.invest.service;

import br.edu.ufop.invest.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

@Service
public class AuthorizationService implements UserDetailsService {

    private final UserRepository repository;

    public AuthorizationService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userEntity = repository.findByEmail(username)
                .orElseThrow(() -> {
                    System.out.println("ERRO: Usuário não encontrado no banco!");
                    return new UsernameNotFoundException("Usuário não encontrado");
                });

        System.out.println("Usuário encontrado no banco: " + userEntity.getEmail());
        System.out.println("Role: " + userEntity.getRole());
        
        return User.builder()
                .username(userEntity.getEmail())
                .password(userEntity.getPassword())
                .roles(userEntity.getRole().name()) 
                .build();
    }
}