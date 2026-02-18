package br.edu.ufop.invest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ufop.invest.entity.UserEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findById(UUID id);
    UserEntity save(UserEntity entity);
    void deleteById(UUID id);
}
