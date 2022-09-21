package com.example.loanserviceconsumer.repository;

import com.example.loanserviceconsumer.domain.BusinessLoanEvent;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface BusinessLoanRepository extends ReactiveMongoRepository<BusinessLoanEvent,String> {
    Mono<BusinessLoanEvent> findBusinessLoanByCustomerMobileNo(Long mobileNo);
}
