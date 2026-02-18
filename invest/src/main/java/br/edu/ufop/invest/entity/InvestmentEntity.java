package br.edu.ufop.invest.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

import br.edu.ufop.invest.enums.AssetType;
import java.util.UUID;

@Entity
@Table(name = "investments")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvestmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) 
    @Column(name = "investment_id", nullable = false)
    private UUID id; 

    @Enumerated(EnumType.STRING)
    private AssetType type;
    private String symbol;
    private Integer quantity;
    private BigDecimal purchasePrice;
    private LocalDate purchaseDate;
    
}