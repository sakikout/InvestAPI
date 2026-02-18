package br.edu.ufop.dto;

import java.math.BigDecimal;

import br.edu.ufop.invest.enums.AssetType;
import java.util.Map;

public record PortfolioSummaryDTO(
    BigDecimal totalInvested,
    Map<AssetType, BigDecimal> totalByType,
    Long assetCount
) {}