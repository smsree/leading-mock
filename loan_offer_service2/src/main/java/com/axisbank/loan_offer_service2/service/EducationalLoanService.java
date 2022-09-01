package com.axisbank.loan_offer_service2.service;

import com.axisbank.loan_offer_service2.domain.EducationalLoan;
import com.axisbank.loan_offer_service2.repository.EducationalLoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EducationalLoanService {

    @Autowired
    private EducationalLoanRepository educationalLoanRepository;

    public Flux<EducationalLoan> findAllEducationalLoan() {
        return educationalLoanRepository.findAll();
    }

    public Mono<EducationalLoan> addNewEducationalLoan(EducationalLoan educationalLoan) {
        return educationalLoanRepository.save(educationalLoan);
    }

    public Mono<EducationalLoan> findEducationalLoanById(String id) {
        return educationalLoanRepository.findById(id);
    }

    public Mono<EducationalLoan> findEducationalLoanByMobileNo(Long mobileNo) {
        return educationalLoanRepository.findEducationalLoanByCustomerMobileNo(mobileNo);
    }

    public Mono<EducationalLoan> updateExistingLoanById(String id, EducationalLoan updatedEducationalLoan) {
        Mono<EducationalLoan> existingMono = educationalLoanRepository.findById(id);
        return existingMono.flatMap(existingLoan -> {
            existingLoan.setCustomerMobileNo(updatedEducationalLoan.getCustomerMobileNo());
            existingLoan.setCollegeName(updatedEducationalLoan.getCollegeName());
            existingLoan.setLoanName(updatedEducationalLoan.getLoanName());
            existingLoan.setLoanamount(updatedEducationalLoan.getLoanamount());
            existingLoan.setStatus(updatedEducationalLoan.getStatus());
            existingLoan.setRateOfInterest(updatedEducationalLoan.getRateOfInterest());
            return educationalLoanRepository.save(existingLoan);
        });
    }

    public Mono<Void> deleteEducationalLoanById(String id) {
        return educationalLoanRepository.deleteById(id);
    }
}
