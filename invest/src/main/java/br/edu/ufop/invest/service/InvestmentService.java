package br.edu.ufop.invest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;
import br.edu.ufop.invest.enums.AssetType;
import br.edu.ufop.invest.repository.InvestmentRepository;
import br.edu.ufop.invest.dto.InvestmentDTO;
import br.edu.ufop.invest.dto.PortfolioSummaryDTO;
import br.edu.ufop.invest.entity.InvestmentEntity;
import java.util.UUID;
import br.edu.ufop.invest.converter.InvestmentConverter;
import br.edu.ufop.invest.domain.Investment;

@Service
public class InvestmentService {
    @Autowired
    private InvestmentRepository repository;
    @Autowired
    private InvestmentConverter converter;


    // CREATE: Create a new investment
    public Investment createInvestment(InvestmentDTO dto) {

        Investment investment = new Investment(
            dto.type(),
            dto.symbol(),
            dto.quantity(),
            dto.purchasePrice(),
            dto.purchaseDate()
        );  

        InvestmentEntity entity = converter.toEntity(investment);
        InvestmentEntity saved = repository.save(entity);
        return converter.toDomain(saved);
    }

    // UPDATE: Update an existing investment
    public Investment updateInvestment(UUID id, InvestmentDTO dto) {
        Optional<InvestmentEntity> existing = repository.findById(id);
        if (existing.isPresent()) {
            Investment investment = converter.toDomain(existing.get());
            investment.setType(dto.type());
            investment.setSymbol(dto.symbol());
            investment.setQuantity(dto.quantity());
            investment.setPurchasePrice(dto.purchasePrice());
            investment.setPurchaseDate(dto.purchaseDate());
            InvestmentEntity entity = converter.toEntity(investment);
            InvestmentEntity saved = repository.save(entity);
            return converter.toDomain(saved);
        }
        return null;
    }

    // DELETE: Delete an existing investment
    public void deleteInvestment(UUID id) {
        repository.deleteById(id);
    }

    // READ ALL: Get all investments
    public List<Investment> findAll(AssetType type) {
        if (type != null) {
            List<InvestmentEntity> entities = repository.findByType(type);
            return entities.stream()
                .map(converter::toDomain)
                .collect(Collectors.toList());
        } else {
            List<InvestmentEntity> entities = repository.findAll();
            return entities.stream()
                .map(converter::toDomain)
                .collect(Collectors.toList());
        }
    }

    // READ BY ID: Get an investment by ID
    public Investment findById(UUID id) {
        Optional<InvestmentEntity> existing = repository.findById(id);
        if (existing.isPresent()) {
            return converter.toDomain(existing.get());
        }
        return null;
    }

    // READ BY TYPE: Get investments by type
    public List<Investment> findByType(AssetType type) {
        List<InvestmentEntity> entities = repository.findByType(type);
        return entities.stream()
            .map(converter::toDomain)
            .collect(Collectors.toList());
    }

    // READ SUMMARY: Get the summary of the portfolio
    public PortfolioSummaryDTO getSummary() {
        List<InvestmentEntity> entities = repository.findAll();
        List<Investment> all = entities.stream()
            .map(converter::toDomain)
            .collect(Collectors.toList());

        BigDecimal totalInvested = all.stream()
            .map(i -> i.getPurchasePrice().multiply(BigDecimal.valueOf(i.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<AssetType, BigDecimal> totalByType = all.stream()
            .collect(Collectors.groupingBy(
                Investment::getType,
                Collectors.reducing(
                    BigDecimal.ZERO,
                    i -> i.getPurchasePrice().multiply(BigDecimal.valueOf(i.getQuantity())),
                    BigDecimal::add
                )
            ));

        return new PortfolioSummaryDTO(totalInvested, totalByType, (long) all.size());
    }
}