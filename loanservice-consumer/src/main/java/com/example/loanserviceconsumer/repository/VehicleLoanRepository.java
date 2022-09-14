package com.example.loanserviceconsumer.repository;

import com.example.loanserviceconsumer.domain.VehicleLoanEvent;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface VehicleLoanRepository extends ReactiveMongoRepository<VehicleLoanEvent,String > {
    Mono<VehicleLoanEvent> findVehicleLoanByCustomerMobileNo(Long mobileNo);
}