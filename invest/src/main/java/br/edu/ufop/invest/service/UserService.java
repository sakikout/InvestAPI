package br.edu.ufop.invest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import br.edu.ufop.invest.repository.UserRepository;
import br.edu.ufop.invest.converter.UserConverter;
import br.edu.ufop.invest.domain.User;
import br.edu.ufop.invest.dto.UserDTO;
import br.edu.ufop.invest.entity.UserEntity;
import br.edu.ufop.invest.exception.ResourceNotFoundException;
import br.edu.ufop.invest.entity.PortfolioEntity;
import br.edu.ufop.invest.repository.PortfolioRepository;
import br.edu.ufop.invest.exception.UserAlreadyExistsException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PortfolioRepository portfolioRepository;

    // CREATE: User
    @Transactional
    public User createUser(UserDTO userDTO) {
        
        if (userRepository.findByEmail(userDTO.email()).isPresent()) {
            throw new UserAlreadyExistsException("Já existe um usuário com o email: " + userDTO.email());
        }

        User domainUser = User.builder()
                .name(userDTO.name())
                .email(userDTO.email())
                .password(userDTO.password())
                .role(userDTO.role())
                .build();

        UserEntity entityToSave = userConverter.toEntity(domainUser);
        UserEntity savedUser = userRepository.save(entityToSave);

        return userConverter.toDomain(savedUser);
    }

    // READ (All)
    public List<User> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userConverter::toDomain)
                .collect(Collectors.toList());
    }

    // READ (By Id)
    public User getUserById(UUID id) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: " + id));
        
        return userConverter.toDomain(entity);
    }
    
    // READ (By Email)
    public User getUserByEmail(String email) {
        UserEntity entity = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado pelo email: " + email));

        return userConverter.toDomain(entity);
    }

    // UPDATE: Both user and portfolio
    public User updateUser(UUID id, UserDTO userDetails) {
        UserEntity entity = userRepository.findById(id)
             .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: " + id));
        
        entity.setName(userDetails.name());
        entity.setEmail(userDetails.email());
        entity.setPassword(userDetails.password());
        
        UserEntity updatedEntity = userRepository.save(entity);

        return userConverter.toDomain(updatedEntity);
    }

    // DELETE: Both user and portfolio
    public void deleteUser(UUID id) {
        UserEntity entity = userRepository.findById(id)
             .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: " + id));
        
        portfolioRepository.findByUserId(id).ifPresent(portfolio -> {
            portfolioRepository.delete(portfolio);
        });
        
        userRepository.delete(entity);
    }

}