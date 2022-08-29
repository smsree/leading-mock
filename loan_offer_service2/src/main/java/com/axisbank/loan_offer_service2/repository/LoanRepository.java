package com.axisbank.loan_offer_service2.repository;

import com.axisbank.loan_offer_service2.domain.Loan;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends ReactiveMongoRepository<Loan,String> {
}
