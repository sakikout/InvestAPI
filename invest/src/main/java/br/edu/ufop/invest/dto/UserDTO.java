package br.edu.ufop.invest.dto;

import br.edu.ufop.invest.enums.RoleType;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record UserDTO (
    UUID id,
    String name,
    @NotNull(message = "O email é obrigatório")
    String email,
    @NotNull(message = "A senha é obrigatória")
    String password,
    RoleType role,
    PortfolioDTO portfolio
){}
