package br.edu.ufop.invest.service;

import org.springframework.stereotype.Service;
import br.edu.ufop.invest.domain.Portfolio;
import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;
import br.edu.ufop.invest.repository.PortfolioRepository;
import br.edu.ufop.invest.converter.PortfolioConverter;
import br.edu.ufop.invest.entity.PortfolioEntity;
import br.edu.ufop.invest.converter.InvestmentConverter;
import br.edu.ufop.invest.entity.InvestmentEntity;
import br.edu.ufop.invest.domain.Investment;
import lombok.RequiredArgsConstructor;
import br.edu.ufop.invest.repository.UserRepository;
import br.edu.ufop.invest.entity.UserEntity;
import br.edu.ufop.invest.exception.ResourceNotFoundException;
import br.edu.ufop.invest.dto.PortfolioCreateDTO;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository repository;
    private final PortfolioConverter portfolioConverter;
    private final UserRepository userRepository;
    private final InvestmentConverter investmentConverter;

    // CREATE
    @Transactional
    public Portfolio createPortfolio(PortfolioCreateDTO dto) {
        // Find user
        UserEntity user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        List<Investment> investments = dto.investments().stream().map(investmentConverter::toDomainFromDTO).collect(Collectors.toList());
        List<InvestmentEntity> investmentsEntity = investments.stream().map(investmentConverter::toEntity).collect(Collectors.toList());

        // Check if portfolio already exists (Update if it exists)
        Optional<PortfolioEntity> existingPortfolio = repository.findByUserId(user.getId());
        if (existingPortfolio.isPresent()) {
            return updatePortfolio(existingPortfolio.get().getId(), dto); 
        }

        // Create new Portfolio
        PortfolioEntity portfolio = new PortfolioEntity();
        
        portfolio.setUser(user);
        portfolio.setTotalInvested(dto.totalInvested());
        portfolio.setTotalByType(dto.totalByType());
        portfolio.setAssetCount(dto.assetCount());
        
        // Add investments to portfolio
        portfolio.setInvestments(investmentsEntity);

        // Save Portfolio
        PortfolioEntity saved = repository.save(portfolio);
        
        return portfolioConverter.toDomain(saved);
    }

    // READ
    public Portfolio getPortfolio(UUID id) {
        PortfolioEntity portfolioEntity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Nenhum portfolio encontrado com o id: " + id));
        return portfolioConverter.toDomain(portfolioEntity);
    }

    // READ BY USER ID
    public Portfolio getPortfolioByUserId(UUID userId) {
        PortfolioEntity portfolioEntity = repository.findByUserId(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Nenhum portfolio encontrado com o user id: " + userId));
        return portfolioConverter.toDomain(portfolioEntity);
    }

    // UPDATE
    @Transactional
    public Portfolio updatePortfolio(UUID id, PortfolioCreateDTO dto) {
        PortfolioEntity portfolioEntity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Nenhum portfolio encontrado com o id: " + id));

        if (!portfolioEntity.getUser().getId().equals(dto.userId())) {
            UserEntity newUser = userRepository.findById(dto.userId())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado: " + dto.userId()));
            portfolioEntity.setUser(newUser);
        }

        List<Investment> investments = dto.investments().stream().map(investmentConverter::toDomainFromDTO).collect(Collectors.toList());
        List<InvestmentEntity> investmentsEntity = investments.stream().map(investmentConverter::toEntity).collect(Collectors.toList());
        
        portfolioEntity.setTotalInvested(dto.totalInvested());
        portfolioEntity.setTotalByType(dto.totalByType());
        portfolioEntity.setAssetCount(dto.assetCount());
        
        portfolioEntity.getInvestments().clear();
        portfolioEntity.getInvestments().addAll(investmentsEntity);

        PortfolioEntity saved = repository.save(portfolioEntity);
        return portfolioConverter.toDomain(saved);
    }

    // DELETE
    @Transactional
    public void deletePortfolio(UUID id) {
        PortfolioEntity portfolioEntity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Nenhum portfolio encontrado com o id: " + id));

        UserEntity donoDoPortfolio = portfolioEntity.getUser();
        
        if (donoDoPortfolio != null) {
            donoDoPortfolio.setPortfolio(null);
            userRepository.save(donoDoPortfolio); 
        }

        repository.delete(portfolioEntity);
    }

    // READ ALL
    public List<Portfolio> getAllPortfolios() {
        List<PortfolioEntity> entities = repository.findAll();

        if (entities.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum portfolio encontrado");
        }

        return entities.stream()
            .map(portfolioConverter::toDomain)
            .collect(Collectors.toList());
    }

}
