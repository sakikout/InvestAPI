package br.edu.ufop.invest.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

import br.edu.ufop.invest.enums.RoleType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private UUID id;
    private String name;
    private String email;
    private String password;

    private RoleType role;

    private Portfolio portfolio;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}