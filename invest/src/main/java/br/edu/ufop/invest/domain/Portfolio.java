package br.edu.ufop.invest.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import br.edu.ufop.invest.enums.AssetType;
import java.util.List;
import java.util.stream.Collectors;
import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Portfolio {
    private UUID id;
    private User user;
    private BigDecimal totalInvested;
    private Map<AssetType, BigDecimal> totalByType;
    private List<Investment> investments;
    private Long assetCount;

    public Portfolio(UUID id, User user, List<Investment> investments) {
        this.id = id;
        this.user = user;
        this.investments = investments;

        if (this.investments == null || this.investments.isEmpty()) {
            this.totalInvested = BigDecimal.ZERO;
            this.totalByType = new HashMap<AssetType, BigDecimal>();
            this.assetCount = 0L;

        } else {
            // Calculate total invested, total by type and asset count
            BigDecimal totalInvested = this.investments.stream().map(Investment::getPurchasePrice).reduce(BigDecimal.ZERO, BigDecimal::add);

            // Calculate total by type and convert to BigDecimal
            Map<AssetType, Long> totalByType = this.investments.stream().collect(Collectors.groupingBy(Investment::getType, Collectors.summingLong(Investment::getQuantity)));
            Map<AssetType, BigDecimal> totalByTypeBigDecimal = totalByType.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> BigDecimal.valueOf(entry.getValue())));
            
            // Calculate asset count
            Long assetCount = totalByType.values().stream().reduce(0L, Long::sum);

            // Set values
            this.totalInvested = totalInvested;
            this.totalByType = totalByTypeBigDecimal;
            this.assetCount = assetCount;
        }
    }
}
