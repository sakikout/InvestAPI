package br.edu.ufop.invest.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;

import br.edu.ufop.invest.enums.AssetType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "investments")
@Data

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvestmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AssetType type;

    private String symbol;
    private Integer quantity;
    private BigDecimal purchasePrice;
    private LocalDate purchaseDate;
    
}