package com.axisbank.service_application_customer_loan.controller;

import com.axisbank.service_application_customer_loan.client.*;
import com.axisbank.service_application_customer_loan.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/clientInfo")
public class ClientInfoController {

    @Autowired
    BusinessLoanWebClient businessLoanWebClient;
    @Autowired
    CustomerWebClient customerWebClient;
    @Autowired
    EducationalLoanWebclient educationalLoanWebclient;
    @Autowired
    HousingLoanWebClient housingLoanWebClient;
    @Autowired
    VehicleLoanWebClient vehicleLoanWebClient;

    @GetMapping("/{customerMobileNo}")
    public Mono<ClientInfoLoans> retriveAllInfoAboutACustomerViaMobileNo(@PathVariable("customerMobileNo") Long mobileNo){
        Mono<Customer> customerMono = customerWebClient.retriveCustomerInfoByMobileNo(mobileNo);
        Customer customer = customerMono.block();
        BusinessLoan businessLoan = businessLoanWebClient.retriveBusinessLoanUsingMobileNo(mobileNo).block();
        EducationalLoan educationalLoan = educationalLoanWebclient.retriveEducationalLoanDetailsViaMobileNo(mobileNo).block();
        HousingLoan housingLoan = housingLoanWebClient.returiveHousingLoanUsingMobileNo(mobileNo).block();
        VehicalLoan vehicalLoan = vehicleLoanWebClient.retriveVehicleloanUsingMobileNo(mobileNo).block();
        return Mono.just(new ClientInfoLoans(customer,businessLoan,educationalLoan,housingLoan,vehicalLoan));
    }
}
