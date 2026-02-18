package br.edu.ufop.invest.converter;

import org.springframework.stereotype.Component;

import br.edu.ufop.invest.entity.UserEntity;
import br.edu.ufop.invest.domain.User;
import br.edu.ufop.invest.dto.UserDTO;
import br.edu.ufop.invest.domain.Portfolio;
import br.edu.ufop.invest.entity.PortfolioEntity;
import br.edu.ufop.invest.dto.PortfolioDTO;


@Component
public class UserConverter {

    // Entidade (Banco) -> Domínio (Negócio)
    public User toDomain(UserEntity entity) {
        if (entity == null) return null;


        PortfolioConverter portfolioConverter = new PortfolioConverter();
        Portfolio portfolio = portfolioConverter.toDomain(entity.getPortfolio());
        

        return User.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .role(entity.getRole())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .portfolio(portfolio)
                .build();
    }

    // Domínio (Negócio) -> Entidade (Banco)
    public UserEntity toEntity(User domain) {
        if (domain == null) return null;

        PortfolioConverter portfolioConverter = new PortfolioConverter();
        PortfolioEntity portfolioEntity = portfolioConverter.toEntity(domain.getPortfolio());

        return UserEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .email(domain.getEmail())
                .password(domain.getPassword())
                .role(domain.getRole())
                .portfolio(portfolioEntity)
                .build();
    }

    // Domínio (Negócio) -> DTO (JSON)
    public UserDTO toDTO(User domain) {
        if (domain == null) return null;

        PortfolioConverter portfolioConverter = new PortfolioConverter();
        PortfolioDTO portfolioDTO = portfolioConverter.toDTO(domain.getPortfolio());

        return new UserDTO(
            domain.getId(),
            domain.getName(),
            domain.getEmail(),
            domain.getPassword(),
            domain.getRole(),
            portfolioDTO
        );
    }

    // DTO (JSON) -> Domínio (Negócio)
    public User toDomainFromDTO(UserDTO dto) {
        if (dto == null) return null;

        PortfolioConverter portfolioConverter = new PortfolioConverter();
        Portfolio portfolio = portfolioConverter.toDomainFromDTO(dto.portfolio());

        return User.builder()
            .id(dto.id())
            .name(dto.name())
            .email(dto.email())
            .password(dto.password())
            .role(dto.role())
            .portfolio(portfolio)
            .build();
    }

    public UserDTO toSummaryDTO(User domain) {
        if (domain == null) return null;

        return new UserDTO(
            domain.getId(),
            domain.getName(),
            domain.getEmail(),
            null,
            domain.getRole(),
            null
        );
    }
}