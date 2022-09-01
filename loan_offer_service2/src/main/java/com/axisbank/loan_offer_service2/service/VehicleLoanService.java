package com.axisbank.loan_offer_service2.service;

import com.axisbank.loan_offer_service2.domain.VehicleLoan;
import com.axisbank.loan_offer_service2.repository.VehicleLoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class VehicleLoanService {
    @Autowired
    private VehicleLoanRepository vehicleLoanRepository;


    public Mono<VehicleLoan> addNewLoan(VehicleLoan vehicleLoan) {
        return vehicleLoanRepository.save(vehicleLoan);
    }

    public Mono<VehicleLoan> getVehicleLoanById(String id) {
        return vehicleLoanRepository.findById(id);
    }

    public Mono<VehicleLoan> getVehicleLoanByMobileNo(Long mobileNo) {
        return vehicleLoanRepository.findVehicleLoanByCustomerMobileNo(mobileNo);
    }

    public Mono<VehicleLoan> updateVehcileLoanById(String id, VehicleLoan updateVehicleLoan) {
        Mono<VehicleLoan> existingMono = vehicleLoanRepository.findById(id);
        return existingMono
                .flatMap(existingLoan->{
                    existingLoan.setCustomerMobileNo(updateVehicleLoan.getCustomerMobileNo());
                    existingLoan.setVehicleName(updateVehicleLoan.getVehicleName());
                    existingLoan.setLoanName(updateVehicleLoan.getLoanName());
                    existingLoan.setLoanamount(updateVehicleLoan.getLoanamount());
                    existingLoan.setRateOfInterest(updateVehicleLoan.getRateOfInterest());
                    existingLoan.setStatus(updateVehicleLoan.getStatus());
                    return vehicleLoanRepository.save(existingLoan);
                });
    }

    public Mono<Void> deleteVehicleLoanById(String id) {
        return vehicleLoanRepository.deleteById(id);
    }

    public Flux<VehicleLoan> getAllVehicleLoan() {
        return vehicleLoanRepository.findAll();
    }
}
