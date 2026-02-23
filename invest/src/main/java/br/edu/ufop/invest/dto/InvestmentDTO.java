package br.edu.ufop.invest.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;
import br.edu.ufop.invest.enums.AssetType;
import java.time.LocalDate;

public record InvestmentDTO(
    UUID id,
    @NotNull(message = "O tipo de ativo é obrigatório")
    AssetType type,
    @NotBlank(message = "O símbolo é obrigatório")
    String symbol,
    @NotNull(message = "A quantidade é obrigatória")
    Integer quantity,
    @NotNull(message = "O preço de compra é obrigatório")
    BigDecimal purchasePrice,
    @NotNull(message = "A data de compra é obrigatória")
    LocalDate purchaseDate
) {}
