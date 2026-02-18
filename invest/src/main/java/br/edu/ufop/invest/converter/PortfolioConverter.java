package br.edu.ufop.invest.converter;

import org.springframework.stereotype.Component;
import br.edu.ufop.invest.entity.PortfolioEntity;
import br.edu.ufop.invest.domain.Portfolio;
import br.edu.ufop.invest.domain.User;
import br.edu.ufop.invest.dto.PortfolioDTO;
import br.edu.ufop.invest.dto.UserDTO;
import java.util.stream.Collectors;
import java.util.List;
import br.edu.ufop.invest.dto.InvestmentDTO;

@Component
public class PortfolioConverter {
    
    // Entidade (Banco) -> Domínio (Negócio)
    public Portfolio toDomain(PortfolioEntity entity) {
        if (entity == null) return null;

        InvestmentConverter investmentConverter = new InvestmentConverter();
        
        User userDomain = null;
        if (entity.getUser() != null) {
            userDomain = new User();
            userDomain.setId(entity.getUser().getId());
            userDomain.setName(entity.getUser().getName());
            userDomain.setEmail(entity.getUser().getEmail());
        }

        return new Portfolio(
            entity.getId(), 
            userDomain, 
            entity.getInvestments().stream().map(investmentConverter::toDomain).collect(Collectors.toList())
        );
    }

    // Domínio (Negócio) -> Entidade (Banco)
    public PortfolioEntity toEntity(Portfolio domain) {
        if (domain == null) return null;

        PortfolioEntity entity = new PortfolioEntity();
        UserConverter userConverter = new UserConverter();
        InvestmentConverter investmentConverter = new InvestmentConverter();

        entity.setId(domain.getId());
        
        entity.setUser(userConverter.toEntity(domain.getUser()));
        
        entity.setTotalInvested(domain.getTotalInvested());
        entity.setTotalByType(domain.getTotalByType());
        entity.setAssetCount(domain.getAssetCount());
        
        if (domain.getInvestments() != null) {
             entity.setInvestments(domain.getInvestments().stream()
                 .map(investmentConverter::toEntity)
                 .collect(Collectors.toList()));
        }
        
        return entity;
    }

    // Domínio (Negócio) -> DTO (JSON)
    public PortfolioDTO toDTO(Portfolio domain) {
        if (domain == null) return null;

        InvestmentConverter investmentConverter = new InvestmentConverter();
        
        UserDTO userDTO = null;
        if (domain.getUser() != null) {
            UserConverter userConverter = new UserConverter();
            userDTO = userConverter.toSummaryDTO(domain.getUser());
        }

        List<InvestmentDTO> investmentDTOs = domain.getInvestments().stream()
            .map(investmentConverter::toDTO)
            .collect(Collectors.toList());

        return new PortfolioDTO(
            domain.getId(),
            userDTO,
            investmentDTOs,
            domain.getTotalInvested(),
            domain.getTotalByType(),
            domain.getAssetCount()
        );
    }

    public Portfolio toDomainFromDTO(PortfolioDTO dto) {
        if (dto == null) return null;

        UserConverter userConverter = new UserConverter();
        InvestmentConverter investmentConverter = new InvestmentConverter();

        return new Portfolio(
            dto.id(),
            userConverter.toDomainFromDTO(dto.user()), 
            dto.investments().stream().map(investmentConverter::toDomainFromDTO).collect(Collectors.toList())
        );
    }
}