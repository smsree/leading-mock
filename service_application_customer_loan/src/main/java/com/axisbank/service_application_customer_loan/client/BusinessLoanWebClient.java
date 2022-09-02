package com.axisbank.service_application_customer_loan.client;

import com.axisbank.service_application_customer_loan.domain.BusinessLoan;
import com.axisbank.service_application_customer_loan.exception.BusinessLoanClientException;
import com.axisbank.service_application_customer_loan.exception.BusinessLoanServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class BusinessLoanWebClient {

    @Autowired
    private WebClient webClient;

    @Value("${restClient.businessUrl}")
    private String businessUrl;

    public Mono<BusinessLoan> retriveBusinessLoanUsingMobileNo(Long mobileNo){
        String url = businessUrl.concat("/{mobileNo}");
        return webClient
                .get()
                .uri(url,mobileNo)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,clientResponse -> {
                    log.error("Business lona Status code is: {}",clientResponse.statusCode().value());
                    if(clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)){
                        return Mono.empty();
                    }
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(responseMessage->Mono.error(new BusinessLoanClientException(
                                    responseMessage
                            )));
                })
                .onStatus(HttpStatus::is5xxServerError,clientResponse -> {
                    log.error("Status code is: {}",clientResponse.statusCode().value());
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(responseMessage->Mono.error(new BusinessLoanServerException(
                                    "Business lona Server exception is :"+responseMessage
                            )));
                })
                .bodyToMono(BusinessLoan.class)
                .log();
    }
}
