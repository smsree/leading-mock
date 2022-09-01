package com.axisbank.loan_offer_service2.controller;

import com.axisbank.loan_offer_service2.domain.Loan;
import com.axisbank.loan_offer_service2.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

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
    public Mono<Loan> addNewLoan(@RequestBody @Valid Loan educationalloan){
        return loanService.addNewLoan(educationalloan);
    }
    @GetMapping("/all")
    public Flux<Loan> getAllLoan(){
        return loanService.getAllLoan();
    }
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Loan>> getLoanById(@PathVariable("id") String loanId){

        return loanService.getLoanById(loanId)
                .map(educationalloan -> {
                    return ResponseEntity.ok().body(educationalloan);
                })
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Loan>> updateLoanById(@PathVariable("id") String id, @RequestBody Loan educationalloan){
        return loanService.updateLoanById(id, educationalloan)
                .map(educationalloan1 ->{
                    return ResponseEntity.ok().body(educationalloan1);
                })
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteLoanById(@PathVariable("id") String id){
        return loanService.deleteById(id);
    }

    @GetMapping("/customerMobile/{mobileNo}")
    public Flux<Loan> findAllLoanByMobileNumber(@PathVariable("mobileNo") Long mobileNo){
        return loanService.getAllLoanByMobileNo(mobileNo);
    }

}
