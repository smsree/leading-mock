package com.axisbank.loanappofferservicewebclient.controller;

import com.axisbank.loanappofferservicewebclient.client.CustomerWebClient;
import com.axisbank.loanappofferservicewebclient.client.LoanWebClient;
import com.axisbank.loanappofferservicewebclient.domain.CustomerLoan;
import com.axisbank.loanappofferservicewebclient.domain.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/v1/customerLoan")
public class CustomerLoanController {
    @Autowired
    private CustomerWebClient customerWebClient;
    @Autowired
    private LoanWebClient loanWebClient;

    @GetMapping("/{mobileNo}")
    public Mono<CustomerLoan> findLoanDetailsAndCustomerInfoByMobileNo(@PathVariable("mobileNo") Long mobileNo){
        return customerWebClient
                .retriveCustomerInfoByMobileNo(mobileNo)
                .flatMap(customer -> {
                    Flux<Loan> loanFlux = loanWebClient.reteriveallLoanInfoByCustomerMobile(mobileNo);
                    Mono<List<Loan>> listMonoLoan = loanFlux.collectList();
                    return listMonoLoan.map(loans -> {
                        return new CustomerLoan(customer,loans);
                    });
                });
    }
}
