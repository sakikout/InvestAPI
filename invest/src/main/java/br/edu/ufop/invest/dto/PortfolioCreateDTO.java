package br.edu.ufop.invest.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import br.edu.ufop.invest.enums.AssetType;
import java.util.Map;


public record PortfolioCreateDTO(
    UUID userId, 
    BigDecimal totalInvested,
    Map<AssetType, BigDecimal> totalByType,
    Long assetCount,
    List<InvestmentDTO> investments
) {}