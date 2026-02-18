package br.edu.ufop.invest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ufop.invest.enums.AssetType;
import br.edu.ufop.invest.entity.InvestmentEntity;
import java.util.Optional;
import java.util.UUID;


public interface InvestmentRepository extends JpaRepository<InvestmentEntity, Long> {
    List<InvestmentEntity> findByType(AssetType type);
    List<InvestmentEntity> findAll();
    Optional<InvestmentEntity> findById(UUID id);
    InvestmentEntity save(InvestmentEntity entity);
    void deleteById(UUID id);
}