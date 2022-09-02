package com.axisbank.loan_offer_service2.controller;

import com.axisbank.loan_offer_service2.domain.BusinessLoan;
import com.axisbank.loan_offer_service2.service.BusinessLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/businessLoan")
public class BusinessLoanController {
    @Autowired
    private BusinessLoanService businessLoanService;

    @GetMapping("/")
    public Mono<String>  welcomeMessage(){
        return Mono.just("Welcome to the axis bank business loan section");

    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BusinessLoan> addNewLoan(@RequestBody BusinessLoan businessLoan){
        return businessLoanService.addNewBusinessLoan(businessLoan);
    }

    @GetMapping("/allBuisnessLoan")
    public Flux<BusinessLoan> getAllBusinessLoan(){
        return businessLoanService.getAllBusinessLoanAvailable();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<BusinessLoan>> getBusinessLoanById(@PathVariable("id") String id){
        return businessLoanService.findBusinessLoanById(id)
                .map(businessLoan -> {
                    return ResponseEntity.ok().body(businessLoan);
                })
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @GetMapping("/customerMobileNo/{mobileNo}")
    public Mono<ResponseEntity<BusinessLoan>> getBusinessLoanByCustomerMobileNo(@PathVariable("mobileNo") Long mobileNo){
        return businessLoanService.findBusinessLoanByMobileNo(mobileNo)
                .map(businessLoan -> {
                    return ResponseEntity.ok().body(businessLoan);
                })
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<BusinessLoan>> updateExistingBusinessLoanById(@PathVariable("id") String id,@RequestBody BusinessLoan updatedBusinessLoan){
        return businessLoanService.updateExistingBusinessLoanByIdService(id,updatedBusinessLoan)
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
