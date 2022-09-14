package com.example.loanserviceconsumer.controller;

import com.example.loanserviceconsumer.domain.HousingLoanEvent;
import com.example.loanserviceconsumer.service.HousingLoanControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/housingLoan")
public class HousingLoanEventController {
    @Autowired
    HousingLoanControllerService housingLoanService;

    @GetMapping("/")
    public Mono<String> welcomeMessage(){
        return Mono.just("Welcome to axis bank housing loan section");
    }
    @GetMapping("/allHousingLoan")
    public Flux<HousingLoanEvent> getallHousingLoan(){
        return housingLoanService.findAllHousingLoan();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<HousingLoanEvent>> findHousingLoanById(@PathVariable("id") String id){
        return housingLoanService.findHousingLoanById(id)
                .map(housingLoan -> {
                    return ResponseEntity.ok().body(housingLoan);
                })
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
    @GetMapping("/customerMobileNo/{mobileNo}")
    public Mono<ResponseEntity<HousingLoanEvent>> findHousingLoanByCustomerMobileNo(@PathVariable("mobileNo") Long mobileNo){
        return housingLoanService.findHousingLoanByMobileNo(mobileNo)
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
