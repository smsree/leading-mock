package com.example.loanserviceconsumer.controller;

import com.example.loanserviceconsumer.domain.EducationalLoanEvent;
import com.example.loanserviceconsumer.service.EducationalLoanControllerEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/educationalLoan")
public class EducationalLoanEventController {

    @Autowired
    EducationalLoanControllerEventService educationalLoanService;
    @GetMapping("/")
    public Mono<String> welcomeMessage(){
        return Mono.just("Welcome to the axis bank educational loan section");
    }

    @GetMapping("/allEducationalLoan")
    public Flux<EducationalLoanEvent> getAllEducationalLoan(){
        return educationalLoanService.findAllEducationalLoan();
    }
    @GetMapping("/{id}")
    public Mono<ResponseEntity<EducationalLoanEvent>> getEducationalLoanById(@PathVariable("id") String id){
        return educationalLoanService.findEducationalLoanById(id)
                .map(educationalLoan -> {
                    return ResponseEntity.ok().body(educationalLoan);
                })
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
    @GetMapping("/customerMobileNo/{mobileNo}")
    public Mono<ResponseEntity<EducationalLoanEvent>> getEducationalLoanByMobileNo(@PathVariable("mobileNo") Long mobileNo){
        return educationalLoanService.findEducationalLoanByMobileNo(mobileNo)
                .map(educationalLoan -> {
                    return ResponseEntity.ok().body(educationalLoan);
                })
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteEducationalLoanById(@PathVariable("id") String id){
        return educationalLoanService.deleteEducationalLoanById(id);
    }

}
