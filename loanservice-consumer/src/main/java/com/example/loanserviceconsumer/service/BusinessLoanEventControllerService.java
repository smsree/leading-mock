package com.example.loanserviceconsumer.service;

import com.example.loanserviceconsumer.domain.BusinessLoanEvent;
import com.example.loanserviceconsumer.repository.BusinessLoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BusinessLoanEventControllerService {
    @Autowired
    private BusinessLoanRepository businessLoanRepository;

    public Flux<BusinessLoanEvent> getAllBusinessLoanAvailable() {
        return businessLoanRepository.findAll();
    }

    public Mono<BusinessLoanEvent> findBusinessLoanById(String id) {
        return businessLoanRepository.findById(id);
    }

    public Mono<BusinessLoanEvent> findBusinessLoanByMobileNo(Long mobileNo) {
        return businessLoanRepository.findBusinessLoanByCustomerMobileNo(mobileNo);
    }
    public Mono<Void> deleteBusinessLoanByIdService(String id) {
        return businessLoanRepository.deleteById(id);
    }

}
