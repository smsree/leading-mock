package com.axisbank.loan_offer_service2.service;

import com.axisbank.loan_offer_service2.domain.HousingLoan;
import com.axisbank.loan_offer_service2.repository.HousingLoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.awt.desktop.PreferencesEvent;

@Service
public class HousingLoanService {
    @Autowired
    private HousingLoanRepository housingLoanRepository;
    public Mono<HousingLoan> addNewHousingLoan(HousingLoan housingLoan) {
        return housingLoanRepository.save(housingLoan);
    }

    public Flux<HousingLoan> findAllHousingLoan() {
        return housingLoanRepository.findAll();
    }

    public Mono<HousingLoan> findHousingLoanById(String id) {
        return housingLoanRepository.findById(id);
    }

    public Mono<HousingLoan> findHousingLoanByMobileNo(Long mobileNo) {
        return housingLoanRepository.findHousingLoanByCustomerMobileNo(mobileNo);
    }

    public Mono<HousingLoan> updateHousingLoanById(String id, HousingLoan updatedHousingLoan) {
        Mono<HousingLoan> availableLoan = housingLoanRepository.findById(id);
        return availableLoan.flatMap(existingLoan->{
            existingLoan.setCustomerMobileNo(updatedHousingLoan.getCustomerMobileNo());
            existingLoan.setAddress(updatedHousingLoan.getAddress());
            existingLoan.setLoanName(updatedHousingLoan.getLoanName());
            existingLoan.setLoanamount(updatedHousingLoan.getLoanamount());
            existingLoan.setRateOfInterest(updatedHousingLoan.getRateOfInterest());
            existingLoan.setStatus(updatedHousingLoan.getStatus());
            return housingLoanRepository.save(existingLoan);
        });
    }

    public Mono<Void> deleteHousingLoanById(String id) {
        return housingLoanRepository.deleteById(id);
    }
}
