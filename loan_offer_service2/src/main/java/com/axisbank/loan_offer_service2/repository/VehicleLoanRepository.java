package com.axisbank.loan_offer_service2.repository;

import com.axisbank.loan_offer_service2.domain.VehicleLoan;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface VehicleLoanRepository extends ReactiveMongoRepository<VehicleLoan,String > {
    Mono<VehicleLoan> findVehicleLoanByCustomerMobileNo(Long mobileNo);
}
