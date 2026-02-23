package br.edu.ufop.invest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ufop.invest.service.PortfolioService;
import br.edu.ufop.invest.domain.Portfolio;
import br.edu.ufop.invest.dto.PortfolioCreateDTO;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/portfolios")
public class PortfolioController {
    
    @Autowired
    private PortfolioService service;

    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        return ResponseEntity.ok("Portfolio service is running");
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Portfolio> createPortfolio(@Valid @RequestBody PortfolioCreateDTO portfolioCreateDTO) {
        Portfolio createdPortfolio = service.createPortfolio(portfolioCreateDTO);
        return new ResponseEntity<>(createdPortfolio, HttpStatus.CREATED);
    }

    // READ (All)
    @GetMapping("/all")
    public ResponseEntity<List<Portfolio>> getAllPortfolios() {
        List<Portfolio> portfolios = service.getAllPortfolios();
        return ResponseEntity.ok(portfolios);
    }

    // READ (By Id)
    @GetMapping("/{id}")
    public ResponseEntity<Portfolio> getPortfolioById(@PathVariable UUID id) {
        Portfolio portfolio = service.getPortfolio(id);
        return ResponseEntity.ok(portfolio);
    }

    // READ (By User Id)
    @GetMapping("/user/{userId}")
    public ResponseEntity<Portfolio> getPortfolioByUserId(@PathVariable UUID userId) {
        Portfolio portfolio = service.getPortfolioByUserId(userId);
        return ResponseEntity.ok(portfolio);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Portfolio> updatePortfolio(@PathVariable UUID id, @Valid @RequestBody PortfolioCreateDTO portfolioCreateDTO) {
        Portfolio updatedPortfolio = service.updatePortfolio(id, portfolioCreateDTO);
        return ResponseEntity.ok(updatedPortfolio);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePortfolio(@PathVariable UUID id) {
        service.deletePortfolio(id);
        return ResponseEntity.ok("Portfolio deletado com sucesso");
    }
}
