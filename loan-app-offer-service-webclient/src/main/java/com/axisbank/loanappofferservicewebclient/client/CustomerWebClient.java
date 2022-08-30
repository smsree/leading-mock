package com.axisbank.loanappofferservicewebclient.client;

import com.axisbank.loanappofferservicewebclient.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomerWebClient {

    @Autowired
    private WebClient webClient;
    @Value("${restClient.customerUrl}")
    private String CustomerUrl;

    public Mono<Customer> retriveCustomerInfoByMobileNo(Long mobileNo){
        String url = CustomerUrl.concat("/{mobileNo}");
        return webClient
                .get()
                .uri(url,mobileNo)
                .retrieve()
                .bodyToMono(Customer.class)
                .log();
    }


}
