package com.axisbank.service_application_customer_loan.client;

import com.axisbank.service_application_customer_loan.domain.HousingLoan;
import com.axisbank.service_application_customer_loan.exception.EducationalLoanClientException;
import com.axisbank.service_application_customer_loan.exception.EducationalLoanServerException;
import com.axisbank.service_application_customer_loan.exception.HousingLoanCleintException;
import com.axisbank.service_application_customer_loan.exception.HousingLoanServerExeption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class HousingLoanWebClient {

    @Autowired
    private WebClient webClient;

    @Value("${restClient.housingUrl}")
    private String housingUrl;

    public Mono<HousingLoan> returiveHousingLoanUsingMobileNo(Long mobileNo){
        String url = housingUrl.concat("/{mobileNo}");
        return webClient
                .get()
                .uri(url,mobileNo)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                    log.error("Housing lona Status code is: {}",clientResponse.statusCode().value());
                    if(clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)){
                        return Mono.empty();
                    }
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(responseMessage->Mono.error(new HousingLoanCleintException(
                                    responseMessage
                            )));
                })
                .onStatus(HttpStatus::is5xxServerError,clientResponse -> {
                    log.error("Status code is: {}",clientResponse.statusCode().value());
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(responseMessage->Mono.error(new HousingLoanServerExeption(
                                    "Housing lona Server exception is :"+responseMessage
                            )));
                })
                .bodyToMono(HousingLoan.class)
                .log();
    }
}
