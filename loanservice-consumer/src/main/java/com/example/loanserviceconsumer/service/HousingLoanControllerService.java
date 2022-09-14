package com.example.loanserviceconsumer.service;

import com.example.loanserviceconsumer.domain.HousingLoanEvent;
import com.example.loanserviceconsumer.repository.HousingLoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class HousingLoanControllerService {
    @Autowired
    private HousingLoanRepository housingLoanRepository;
    public Flux<HousingLoanEvent> findAllHousingLoan() {
        return housingLoanRepository.findAll();
    }

    public Mono<HousingLoanEvent> findHousingLoanById(String id) {
        return housingLoanRepository.findById(id);
    }

    public Mono<HousingLoanEvent> findHousingLoanByMobileNo(Long mobileNo) {
        return housingLoanRepository.findHousingLoanByCustomerMobileNo(mobileNo);
    }
    public Mono<Void> deleteHousingLoanById(String id) {
        return housingLoanRepository.deleteById(id);
    }
}
