package com.axisbank.loan_offer_service2.repository;

import com.axisbank.loan_offer_service2.domain.HousingLoan;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface HousingLoanRepository extends ReactiveMongoRepository<HousingLoan,String> {
    Mono<HousingLoan> findHousingLoanByCustomerMobileNo(Long mobileNo);
}
