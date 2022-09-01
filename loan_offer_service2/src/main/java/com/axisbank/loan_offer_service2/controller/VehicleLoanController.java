package com.axisbank.loan_offer_service2.controller;

import com.axisbank.loan_offer_service2.domain.VehicleLoan;
import com.axisbank.loan_offer_service2.service.VehicleLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/vehicleLoan")
public class VehicleLoanController {
    @Autowired
    private VehicleLoanService vehicleLoanService;

    @GetMapping("/")
    public Mono<String> welcomeMessage(){
        return Mono.just("Welcome to axis bank vehicle loan section");
    }
    @GetMapping("/allVehicleLoan")
    public Flux<VehicleLoan> getAllVehicleLoan(){
        return vehicleLoanService.getAllVehicleLoan();
    }
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<VehicleLoan> addNewVehicleLoan(@RequestBody @Valid  VehicleLoan vehicleLoan){
        return vehicleLoanService.addNewLoan(vehicleLoan);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<VehicleLoan>> getVehicleLoanById(@PathVariable("id") String id){
        return vehicleLoanService.getVehicleLoanById(id)
                .map(vehicleLoan ->{
                    return ResponseEntity.ok().body(vehicleLoan);
                })
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
    @GetMapping("/customerMobileNo/{mobileNo}")
    public Mono<ResponseEntity<VehicleLoan>> getVehicleLoanByMobileNo(@PathVariable("mobileNo") Long mobileNo){
        return vehicleLoanService.getVehicleLoanByMobileNo(mobileNo)
                .map(vehicleLoan -> {
                    return ResponseEntity.ok().body(vehicleLoan);
                })
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
    @PutMapping("/{id}")
    public Mono<ResponseEntity<VehicleLoan>> updateVehicleLoanById(@PathVariable("id") String id,@RequestBody VehicleLoan vehicleLoan){
        return vehicleLoanService.updateVehcileLoanById(id,vehicleLoan)
                .map(loan->{
                    return ResponseEntity.ok().body(loan);
                })
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteExistingLoanById(@PathVariable("id") String id){
        return vehicleLoanService.deleteVehicleLoanById(id);
    }


}
