package com.axisbank.loan_offer_service2.service;

import com.axisbank.loan_offer_service2.domain.Loan;
import com.axisbank.loan_offer_service2.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;

    public Mono<Loan> addNewLoan(Loan educationalloan){
        return loanRepository.save(educationalloan);
    }

    public Flux<Loan> getAllLoan() {
        return loanRepository.findAll();
    }

    public Mono<Loan> getLoanById(String loanId) {
        return loanRepository.findById(loanId);
    }

    public Mono<Loan> updateLoanById(String id, Loan updatedEducationalloan) {
        Mono<Loan> availableLoan = loanRepository.findById(id);
        return availableLoan
                .flatMap(educationalloan -> {
                    educationalloan.setCustomerMobileNo(updatedEducationalloan.getCustomerMobileNo());
                    educationalloan.setLoanName(updatedEducationalloan.getLoanName());
                    educationalloan.setLoanamount(updatedEducationalloan.getLoanamount());
                    educationalloan.setRateOfInterest(updatedEducationalloan.getRateOfInterest());
                    educationalloan.setStatus(updatedEducationalloan.getStatus());
                    return loanRepository.save(educationalloan);
                });
    }

    public Mono<Void> deleteById(String id) {
        return loanRepository.deleteById(id);
    }

    public Flux<Loan> getAllLoanByMobileNo(Long mobileNo) {
        return loanRepository.findLoanByCustomerMobileNo(mobileNo);
    }
}
