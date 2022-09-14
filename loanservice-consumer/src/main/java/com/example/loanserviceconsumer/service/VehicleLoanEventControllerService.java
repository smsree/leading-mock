package com.example.loanserviceconsumer.service;

import com.example.loanserviceconsumer.domain.VehicleLoanEvent;
import com.example.loanserviceconsumer.repository.VehicleLoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class VehicleLoanEventControllerService {
    @Autowired
    private VehicleLoanRepository vehicleLoanRepository;
    public Mono<VehicleLoanEvent> getVehicleLoanById(String id) {
        return vehicleLoanRepository.findById(id);
    }

    public Mono<VehicleLoanEvent> getVehicleLoanByMobileNo(Long mobileNo) {
        return vehicleLoanRepository.findVehicleLoanByCustomerMobileNo(mobileNo);
    }
    public Mono<Void> deleteVehicleLoanById(String id) {
        return vehicleLoanRepository.deleteById(id);
    }

    public Flux<VehicleLoanEvent> getAllVehicleLoan() {
        return vehicleLoanRepository.findAll();
    }
}
