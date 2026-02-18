package br.edu.ufop.invest.converter;

import org.springframework.stereotype.Component;

import br.edu.ufop.invest.entity.InvestmentEntity;
import br.edu.ufop.invest.domain.Investment;
import br.edu.ufop.invest.dto.InvestmentDTO;


@Component
public class InvestmentConverter {

    // Domínio (Negócio) -> Entidade (Banco)
    public Investment toDomain(InvestmentEntity entity) {
        if (entity == null) return null;

        return new Investment(
            entity.getType(),
            entity.getSymbol(),
            entity.getQuantity(),
            entity.getPurchasePrice(),
            entity.getPurchaseDate()
        );
    }

    // Entidade (Banco) -> Domínio (Negócio)
    public InvestmentEntity toEntity(Investment domain) {
        if (domain == null) return null;

        InvestmentEntity entity = new InvestmentEntity();
        
        entity.setType(domain.getType());
        entity.setSymbol(domain.getSymbol());
        entity.setQuantity(domain.getQuantity());
        entity.setPurchasePrice(domain.getPurchasePrice());
        entity.setPurchaseDate(domain.getPurchaseDate());
        return entity;
    }

    // Domínio (Negócio) -> DTO (JSON)
    public InvestmentDTO toDTO(Investment domain) {
        if (domain == null) return null;

        return new InvestmentDTO(
            domain.getType(),
            domain.getSymbol(),
            domain.getQuantity(),
            domain.getPurchasePrice(),
            domain.getPurchaseDate()
        );
    }

    // DTO (JSON) -> Domínio (Negócio)
    public Investment toDomainFromDTO(InvestmentDTO dto) {
        if (dto == null) return null;

        return new Investment(
            dto.type(),
            dto.symbol(),
            dto.quantity(),
            dto.purchasePrice(),
            dto.purchaseDate()
        );
    }
}