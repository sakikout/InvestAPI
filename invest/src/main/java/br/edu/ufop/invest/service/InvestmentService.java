package br.edu.ufop.invest.service;

import org.springframework.beans.BeanUtils;
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

@Service
public class InvestmentService {

    @Autowired
    private InvestmentRepository repository;

    public InvestmentEntity create(InvestmentDTO dto) {
        InvestmentEntity investment = new InvestmentEntity();
        BeanUtils.copyProperties(dto, investment); 
        return repository.save(investment);
    }

    public List<InvestmentEntity> findAll(AssetType type) {
        if (type != null) {
            return repository.findByType(type);
        }
        return repository.findAll();
    }
    
    public InvestmentEntity update(Long id, InvestmentDTO dto) {
        Optional<InvestmentEntity> existing = repository.findById(id);
        if (existing.isPresent()) {
            InvestmentEntity investment = existing.get();
            BeanUtils.copyProperties(dto, investment);
            return repository.save(investment);
        }
        return null;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public InvestmentEntity findById(Long id) {
        Optional<InvestmentEntity> existing = repository.findById(id);
        if (existing.isPresent()) {
            return existing.get();
        }
        return null;
    }

    public PortfolioSummaryDTO getSummary() {
        List<InvestmentEntity> all = repository.findAll();

        BigDecimal totalInvested = all.stream()
            .map(i -> i.getPurchasePrice().multiply(BigDecimal.valueOf(i.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<AssetType, BigDecimal> totalByType = all.stream()
            .collect(Collectors.groupingBy(
                InvestmentEntity::getType,
                Collectors.reducing(
                    BigDecimal.ZERO,
                    i -> i.getPurchasePrice().multiply(BigDecimal.valueOf(i.getQuantity())),
                    BigDecimal::add
                )
            ));

        return new PortfolioSummaryDTO(totalInvested, totalByType, (long) all.size());
    }
}