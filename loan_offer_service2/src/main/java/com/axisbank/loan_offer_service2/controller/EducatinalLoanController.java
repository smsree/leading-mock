package com.axisbank.loan_offer_service2.controller;

import com.axisbank.loan_offer_service2.domain.EducationalLoan;
import com.axisbank.loan_offer_service2.service.EducationalLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/educationalLoan")
public class EducatinalLoanController {
    @Autowired
    private EducationalLoanService educationalLoanService;

    @GetMapping("/")
    public Mono<String> welcomeMessage(){
        return Mono.just("Welcome to the axis bank educational loan section");
    }

    @GetMapping("/allEducationalLoan")
    public Flux<EducationalLoan> getAllEducationalLoan(){
        return educationalLoanService.findAllEducationalLoan();
    }
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<EducationalLoan> addEducationalLoan(@RequestBody @Valid  EducationalLoan educationalLoan){
        return educationalLoanService.addNewEducationalLoan(educationalLoan);
    }
    @GetMapping("/{id}")
    public Mono<ResponseEntity<EducationalLoan>> getEducationalLoanById(@PathVariable("id") String id){
        return educationalLoanService.findEducationalLoanById(id)
                .map(educationalLoan -> {
                    return ResponseEntity.ok().body(educationalLoan);
                })
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
    @GetMapping("/customerMobileNo/{mobileNo}")
    public Mono<ResponseEntity<EducationalLoan>> getEducationalLoanByMobileNo(@PathVariable("mobileNo") Long mobileNo){
        return educationalLoanService.findEducationalLoanByMobileNo(mobileNo)
                .map(educationalLoan -> {
                    return ResponseEntity.ok().body(educationalLoan);
                })
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
    @PutMapping("/{id}")
    public Mono<ResponseEntity<EducationalLoan>> updateExistingLoanById(@PathVariable("id") String id,@RequestBody EducationalLoan educationalLoan){
        return educationalLoanService.updateExistingLoanById(id,educationalLoan)
                .map(educationalLoan1 -> {
                    return ResponseEntity.ok().body(educationalLoan1);
                })
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteEducationalLoanById(@PathVariable("id") String id){
        return educationalLoanService.deleteEducationalLoanById(id);
    }
}
