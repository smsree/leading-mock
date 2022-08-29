package com.axisbank.loanapp.repository;

import com.axisbank.loanapp.domain.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerRepository extends ReactiveMongoRepository<Customer,String> {

    Mono<Customer> findByMobileNo(Long mobileNo);
}
