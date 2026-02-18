package br.edu.ufop.invest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.edu.ufop.invest.entity.PortfolioEntity;
import java.util.UUID;
import java.util.Optional;


@Repository
public interface PortfolioRepository extends JpaRepository<PortfolioEntity, UUID> {
    Optional<PortfolioEntity> findByUserId(UUID userId);
    PortfolioEntity save(PortfolioEntity entity);
    void deleteById(UUID id);
}
