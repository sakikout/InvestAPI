package br.edu.ufop.invest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.ufop.invest.service.UserService;
import br.edu.ufop.invest.domain.User;
import br.edu.ufop.invest.dto.UserDTO;
import br.edu.ufop.invest.domain.Portfolio;
import br.edu.ufop.invest.service.PortfolioService;
import br.edu.ufop.invest.dto.PortfolioCreateDTO;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PortfolioService portfolioService;

    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        return ResponseEntity.ok("User service is running");
    }

    // CREATE
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO) {
        User createdUser = userService.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // READ (All)
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // READ (By Id)
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @Valid @RequestBody UserDTO userDetails) {
        User updatedUser = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // CREATE PORTFOLIO
    @PostMapping("/{id}/portfolio")
    public ResponseEntity<Portfolio> createPortfolio(@PathVariable UUID id, @Valid @RequestBody PortfolioCreateDTO portfolioCreateDTO) {
        Portfolio portfolio = portfolioService.createPortfolio(portfolioCreateDTO);
        return new ResponseEntity<>(portfolio, HttpStatus.CREATED);
    }
}
