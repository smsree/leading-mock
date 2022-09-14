package com.example.loanserviceconsumer.repository;

import com.example.loanserviceconsumer.domain.EducationalLoanEvent;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface EducationalLoanRepository extends ReactiveMongoRepository<EducationalLoanEvent,String> {

    Mono<EducationalLoanEvent> findEducationalLoanByCustomerMobileNo(Long mobileNo);
}

