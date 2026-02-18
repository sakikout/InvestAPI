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
import br.edu.ufop.invest.entity.InvestmentEntity;
import br.edu.ufop.invest.dto.InvestmentDTO;
import br.edu.ufop.invest.dto.PortfolioSummaryDTO;
import br.edu.ufop.invest.enums.AssetType;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/investments")
public class InvestmentController {

    @Autowired
    private InvestmentService service;

    @PostMapping
    public ResponseEntity<InvestmentEntity> create(@RequestBody InvestmentDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<InvestmentEntity>> list(@RequestParam(required = false) AssetType type) {
        return ResponseEntity.ok(service.findAll(type));
    }

    @GetMapping("/summary")
    public ResponseEntity<PortfolioSummaryDTO> getSummary() {
        return ResponseEntity.ok(service.getSummary());
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvestmentEntity> update(@PathVariable Long id, @RequestBody InvestmentDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
