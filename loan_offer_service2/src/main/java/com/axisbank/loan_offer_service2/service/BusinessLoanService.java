package com.axisbank.loan_offer_service2.service;

import com.axisbank.loan_offer_service2.domain.BusinessLoan;
import com.axisbank.loan_offer_service2.repository.BusinessLoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BusinessLoanService {
    @Autowired
    private BusinessLoanRepository businessLoanRepository;

    public Mono<BusinessLoan> addNewBusinessLoan(BusinessLoan businessLoan) {
        return businessLoanRepository.save(businessLoan);
    }

    public Flux<BusinessLoan> getAllBusinessLoanAvailable() {
        return businessLoanRepository.findAll();
    }

    public Mono<BusinessLoan> findBusinessLoanById(String id) {
        return businessLoanRepository.findById(id);
    }

    public Mono<BusinessLoan> findBusinessLoanByMobileNo(Long mobileNo) {
        return businessLoanRepository.findBusinessLoanByCustomerMobileNo(mobileNo);
    }

    public Mono<BusinessLoan> updateExistingBusinessLoanByIdService(String id,BusinessLoan updatedBusinessLoan) {
        Mono<BusinessLoan> availableLoan = businessLoanRepository.findById(id);
        return availableLoan
                .flatMap(businessLoan -> {
                    businessLoan.setCustomerMobileNo(updatedBusinessLoan.getCustomerMobileNo());
                    businessLoan.setBusinessName(updatedBusinessLoan.getBusinessName());
                    businessLoan.setLoanName(updatedBusinessLoan.getLoanName());
                    businessLoan.setLoanamount(updatedBusinessLoan.getLoanamount());
                    businessLoan.setRateOfInterest(updatedBusinessLoan.getRateOfInterest());
                    businessLoan.setStatus(updatedBusinessLoan.getStatus());
                    return businessLoanRepository.save(businessLoan);
                });
    }

    public Mono<Void> deleteBusinessLoanByIdService(String id) {
        return businessLoanRepository.deleteById(id);
    }
}
