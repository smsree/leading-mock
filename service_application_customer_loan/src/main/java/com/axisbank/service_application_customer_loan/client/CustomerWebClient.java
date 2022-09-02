package com.axisbank.service_application_customer_loan.client;

import com.axisbank.service_application_customer_loan.domain.Customer;
import com.axisbank.service_application_customer_loan.exception.CustomerClientException;
import com.axisbank.service_application_customer_loan.exception.CustomerServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomerWebClient {

    @Autowired
    private WebClient webClient;

    @Value("${restClient.customerUrl}")
    String customerUrl;

    public Mono<Customer> retriveCustomerInfoByMobileNo(Long mobileNo){
        String url = customerUrl.concat("/mobile/{mobileNo}");
        return webClient
                .get()
                .uri(url,mobileNo)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,clientResponse -> {
                    log.error("Status code is: {}",clientResponse.statusCode().value());
                    if(clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)){
                        return Mono.error(new CustomerClientException("There is no customer info available:"+mobileNo,
                                clientResponse.statusCode().value()));
                    }
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(responseMessage->Mono.error(new CustomerClientException(
                                    responseMessage,clientResponse.statusCode().value()
                            )));
                })
                .onStatus(HttpStatus::is5xxServerError,clientResponse -> {
                    log.error("Status code is: {}",clientResponse.statusCode().value());
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(responseMessage->Mono.error(new CustomerServerException(
                                    "Customer Server exception is :"+responseMessage
                            )));
                })
                .bodyToMono(Customer.class)
                .log();
    }
}
