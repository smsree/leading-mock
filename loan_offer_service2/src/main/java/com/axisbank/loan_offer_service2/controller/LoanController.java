package com.axisbank.loan_offer_service2.controller;

import com.axisbank.loan_offer_service2.domain.Loan;
import com.axisbank.loan_offer_service2.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/loan")
public class LoanController {
    @Autowired
    private LoanService loanService;

    @GetMapping("/")
    public Mono<String> welcomeController(){
        return Mono.just("Welcome to axis bank loan section");
    }
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Loan> addNewLoan(@RequestBody Loan loan){
        return loanService.addNewLoan(loan);
    }
    @GetMapping("/all")
    public Flux<Loan> getAllLoan(){
        return loanService.getAllLoan();
    }
    @GetMapping("/{id}")
    public Mono<Loan> getLoanById(@PathVariable("id") String loanId){
        return loanService.getLoanById(loanId);
    }
    @PutMapping("/{id}")
    public Mono<Loan> updateLoanById(@PathVariable("id") String id,@RequestBody Loan loan){
        return loanService.updateLoanById(id,loan);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteLoanById(@PathVariable("id") String id){
        return loanService.deleteById(id);
    }
}
