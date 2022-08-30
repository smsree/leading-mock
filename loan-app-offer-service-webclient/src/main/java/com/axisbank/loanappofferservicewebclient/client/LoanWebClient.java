package com.axisbank.loanappofferservicewebclient.client;

import com.axisbank.loanappofferservicewebclient.domain.Loan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
@Slf4j
public class LoanWebClient {

    @Autowired
    private WebClient webClient;
    @Value("${restClient.LoanUrl}")
    private String LoanUrl;

    public Flux<Loan> reteriveallLoanInfoByCustomerMobile(Long mobileNo){
        String url = LoanUrl.concat("/{mobileNo}");
        return webClient
                .get()
                .uri(url,mobileNo)
                .retrieve()
                .bodyToFlux(Loan.class)
                .log();
    }
}
