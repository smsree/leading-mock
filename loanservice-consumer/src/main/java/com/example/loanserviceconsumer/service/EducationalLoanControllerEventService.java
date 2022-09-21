package com.example.loanserviceconsumer.service;

import com.example.loanserviceconsumer.domain.EducationalLoanEvent;
import com.example.loanserviceconsumer.repository.EducationalLoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EducationalLoanControllerEventService {
    @Autowired
    private EducationalLoanRepository educationalLoanRepository;

    public Flux<EducationalLoanEvent> findAllEducationalLoan() {
        return educationalLoanRepository.findAll();
    }

    public Mono<EducationalLoanEvent> findEducationalLoanById(String id) {
        return educationalLoanRepository.findById(id);
    }

    public Mono<EducationalLoanEvent> findEducationalLoanByMobileNo(Long mobileNo) {
        return educationalLoanRepository.findEducationalLoanByCustomerMobileNo(mobileNo);
    }
    public Mono<Void> deleteEducationalLoanById(String id) {
        return educationalLoanRepository.deleteById(id);
    }
}
