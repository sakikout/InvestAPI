package br.edu.ufop.invest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class AuditingConfig {
    // Habilita a funcionalidade @CreatedDate e @LastModifiedDate
}