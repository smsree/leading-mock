package com.example.loanserviceconsumer.controller;

import com.example.loanserviceconsumer.domain.BusinessLoanEvent;
import com.example.loanserviceconsumer.service.BusinessLoanEventControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/businessLoan")
public class BusinessLoanEventController {
    @Autowired
    private BusinessLoanEventControllerService businessLoanService;

    @GetMapping("/")
    public Mono<String> welcomeMessage(){
        return Mono.just("Welcome to the axis bank business loan section");

    }
    @GetMapping("/allBuisnessLoan")
    public Flux<BusinessLoanEvent> getAllBusinessLoan(){
        return businessLoanService.getAllBusinessLoanAvailable();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<BusinessLoanEvent>> getBusinessLoanById(@PathVariable("id") String id){
        return businessLoanService.findBusinessLoanById(id)
                .map(businessLoan -> {
                    return ResponseEntity.ok().body(businessLoan);
                })
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @GetMapping("/customerMobileNo/{mobileNo}")
    public Mono<ResponseEntity<BusinessLoanEvent>> getBusinessLoanByCustomerMobileNo(@PathVariable("mobileNo") Long mobileNo){


        return businessLoanService.findBusinessLoanByMobileNo(mobileNo)
                .map(businessLoan -> {
                    return ResponseEntity.ok().body(businessLoan);
                })
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteBusinessLoanById(@PathVariable("id") String id){
        return businessLoanService.deleteBusinessLoanByIdService(id);
    }

}
