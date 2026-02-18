package br.edu.ufop.invest.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.List;
import java.util.UUID;

import br.edu.ufop.invest.enums.AssetType;

@Entity
@Table(name = "portfolios")

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private UserEntity user;

    @Column(nullable = false)
    private BigDecimal totalInvested;

    @ElementCollection
    @CollectionTable(name = "portfolio_totals", joinColumns = @JoinColumn(name = "portfolio_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "total_value")
    private Map<AssetType, BigDecimal> totalByType;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
        name = "portfolio_investments", 
        joinColumns = @JoinColumn(name = "portfolio_id"), 
        inverseJoinColumns = @JoinColumn(name = "investment_id")
    )
    private List<InvestmentEntity> investments;

    @Column(nullable = false)
    private Long assetCount;

}
