package com.example.loanserviceconsumer.repository;

import com.example.loanserviceconsumer.domain.HousingLoanEvent;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface HousingLoanRepository extends ReactiveMongoRepository<HousingLoanEvent,String> {
    Mono<HousingLoanEvent> findHousingLoanByCustomerMobileNo(Long mobileNo);
}
