package br.edu.ufop.invest.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import java.math.BigDecimal;
import java.util.Map;
import br.edu.ufop.invest.enums.AssetType;
import java.util.List;

public record PortfolioDTO(
    UUID id,
    @NotNull(message = "O usuário é obrigatório")
    UserDTO user,
    @NotNull(message = "Os ativos são obrigatórios")
    List<InvestmentDTO> investments,
    @NotNull(message = "O total investido é obrigatório")
    BigDecimal totalInvested,
    @NotNull(message = "O total por tipo é obrigatório")
    Map<AssetType, BigDecimal> totalByType,
    @NotNull(message = "O número de ativos é obrigatório")
    Long assetCount
) {
}

