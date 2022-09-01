package com.axisbank.loan_offer_service2.repository;

import com.axisbank.loan_offer_service2.domain.EducationalLoan;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface EducationalLoanRepository extends ReactiveMongoRepository<EducationalLoan,String> {

    Mono<EducationalLoan> findEducationalLoanByCustomerMobileNo(Long mobileNo);
}
