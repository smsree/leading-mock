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

    public Mono<Loan> addNewLoan(Loan loan){
        return loanRepository.save(loan);
    }

    public Flux<Loan> getAllLoan() {
        return loanRepository.findAll();
    }

    public Mono<Loan> getLoanById(String loanId) {
        return loanRepository.findById(loanId);
    }

    public Mono<Loan> updateLoanById(String id,Loan updatedLoan) {
        Mono<Loan> availableLoan = loanRepository.findById(id);
        return availableLoan
                .flatMap(loan -> {
                    loan.setCustomerMobileNo(updatedLoan.getCustomerMobileNo());
                    loan.setLoanName(updatedLoan.getLoanName());
                    loan.setLoanamount(updatedLoan.getLoanamount());
                    loan.setRateOfInterest(updatedLoan.getRateOfInterest());
                    loan.setStatus(updatedLoan.getStatus());
                    return loanRepository.save(loan);
                });
    }

    public Mono<Void> deleteById(String id) {
        return loanRepository.deleteById(id);
    }
}
