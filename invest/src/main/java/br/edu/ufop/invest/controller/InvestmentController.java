package br.edu.ufop.invest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.ufop.invest.service.InvestmentService;
import br.edu.ufop.invest.domain.Investment;
import br.edu.ufop.invest.dto.InvestmentDTO;
import br.edu.ufop.invest.dto.PortfolioSummaryDTO;
import br.edu.ufop.invest.enums.AssetType;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/investments")
public class InvestmentController {

    @Autowired
    private InvestmentService service;

    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        return ResponseEntity.ok("Investment service is running");
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Investment> create(@RequestBody InvestmentDTO dto) {
        Investment investment = service.createInvestment(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(investment);
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<Investment>> list(@RequestParam(required = false) AssetType type) {
        List<Investment> investments = service.findAll(type);
        return ResponseEntity.ok(investments);
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Investment> getById(@PathVariable UUID id) {
        Investment investment = service.findById(id);
        return ResponseEntity.ok(investment);
    }

    // READ SUMMARY
    @GetMapping("/summary")
    public ResponseEntity<PortfolioSummaryDTO> getSummary() {
        PortfolioSummaryDTO summary = service.getSummary();
        return ResponseEntity.ok(summary);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Investment> update(@PathVariable UUID id, @RequestBody InvestmentDTO dto) {
        Investment investment = service.updateInvestment(id, dto);
        return ResponseEntity.ok(investment);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        service.deleteInvestment(id);
        return ResponseEntity.ok("Investimento deletado com sucesso");
    }
}
