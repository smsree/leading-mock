package com.example.loanserviceconsumer.controller;

import com.example.loanserviceconsumer.domain.VehicleLoanEvent;
import com.example.loanserviceconsumer.service.VehicleLoanEventControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/vehicleLoan")
public class VehicleLoanEventController {
    @Autowired
    private VehicleLoanEventControllerService vehicleLoanService;

    @GetMapping("/")
    public Mono<String> welcomeMessage(){
        return Mono.just("Welcome to axis bank vehicle loan section");
    }
    @GetMapping("/allVehicleLoan")
    public Flux<VehicleLoanEvent> getAllVehicleLoan(){
        return vehicleLoanService.getAllVehicleLoan();
    }
    @GetMapping("/{id}")
    public Mono<ResponseEntity<VehicleLoanEvent>> getVehicleLoanById(@PathVariable("id") String id){
        return vehicleLoanService.getVehicleLoanById(id)
                .map(vehicleLoan ->{
                    return ResponseEntity.ok().body(vehicleLoan);
                })
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
    @GetMapping("/customerMobileNo/{mobileNo}")
    public Mono<ResponseEntity<VehicleLoanEvent>> getVehicleLoanByMobileNo(@PathVariable("mobileNo") Long mobileNo){
        return vehicleLoanService.getVehicleLoanByMobileNo(mobileNo)
                .map(vehicleLoan -> {
                    return ResponseEntity.ok().body(vehicleLoan);
                })
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteExistingLoanById(@PathVariable("id") String id){
        return vehicleLoanService.deleteVehicleLoanById(id);
    }
}
