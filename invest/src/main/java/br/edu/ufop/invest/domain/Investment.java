package br.edu.ufop.invest.domain;

import lombok.*;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.UUID;
import br.edu.ufop.invest.enums.AssetType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Investment {
    private UUID id;
    private AssetType type;
    private String symbol;
    private Integer quantity;
    private BigDecimal purchasePrice;
    private LocalDate purchaseDate;


}
