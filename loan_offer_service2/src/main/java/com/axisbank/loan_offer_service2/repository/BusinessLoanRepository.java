package com.axisbank.loan_offer_service2.repository;

import com.axisbank.loan_offer_service2.domain.BusinessLoan;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface BusinessLoanRepository extends ReactiveMongoRepository<BusinessLoan,String> {
    Mono<BusinessLoan> findBusinessLoanByCustomerMobileNo(Long mobileNo);
}
