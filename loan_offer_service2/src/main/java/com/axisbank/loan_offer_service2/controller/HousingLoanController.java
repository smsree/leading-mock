package com.axisbank.loan_offer_service2.controller;

import com.axisbank.loan_offer_service2.domain.HousingLoan;
import com.axisbank.loan_offer_service2.service.HousingLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/housingLoan")
public class HousingLoanController {

    @Autowired
    private HousingLoanService housingLoanService;

    @GetMapping("/")
    public Mono<String> welcomeMessage(){
        return Mono.just("Welcome to axis bank housing loan section");
    }

    @PostMapping("/")
    public Mono<HousingLoan> addNewHousingLoan(@RequestBody @Valid  HousingLoan housingLoan){
        return housingLoanService.addNewHousingLoan(housingLoan);
    }

    @GetMapping("/allHousingLoan")
    public Flux<HousingLoan> getallHousingLoan(){
        return housingLoanService.findAllHousingLoan();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<HousingLoan>> findHousingLoanById(@PathVariable("id") String id){
        return housingLoanService.findHousingLoanById(id)
                .map(housingLoan -> {
                    return ResponseEntity.ok().body(housingLoan);
                })
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
    @GetMapping("/customerMobileNo/{mobileNo}")
    public Mono<ResponseEntity<HousingLoan>> findHousingLoanByCustomerMobileNo(@PathVariable("mobileNo") Long mobileNo){
        return housingLoanService.findHousingLoanByMobileNo(mobileNo)
                .map(housingLoan -> {
                    return ResponseEntity.ok().body(housingLoan);
                })
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
    @PutMapping("/{id}")
    public Mono<ResponseEntity<HousingLoan>> updateHousingLoanById(@PathVariable("id") String id,@RequestBody HousingLoan updatedHousingLoan){
        return housingLoanService.updateHousingLoanById(id,updatedHousingLoan)
                .map(housingLoan -> {
                    return ResponseEntity.ok().body(housingLoan);
                })
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteHousingLoan(@PathVariable("id") String id){
        return housingLoanService.deleteHousingLoanById(id);
    }
}
