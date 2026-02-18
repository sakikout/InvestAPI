package br.edu.ufop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ufop.invest.enums.AssetType;
import br.edu.ufop.invest.entity.InvestmentEntity;
import java.util.Optional;


public interface InvestmentRepository extends JpaRepository<InvestmentEntity, Long> {
    List<InvestmentEntity> findByType(AssetType type);
    List<InvestmentEntity> findAll();
    Optional<InvestmentEntity> findById(Long id);
    InvestmentEntity save(InvestmentEntity investmentEntity);
    void deleteById(Long id);
}