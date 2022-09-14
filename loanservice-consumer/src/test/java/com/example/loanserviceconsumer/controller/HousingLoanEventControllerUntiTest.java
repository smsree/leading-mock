package com.example.loanserviceconsumer.controller;

import com.example.loanserviceconsumer.domain.HousingLoanEvent;
import com.example.loanserviceconsumer.domain.LoanEventType;
import com.example.loanserviceconsumer.service.HousingLoanControllerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = HousingLoanEventController.class)
@AutoConfigureWebTestClient
public class HousingLoanEventControllerUntiTest {
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private HousingLoanControllerService housingLoanService;
    @Test
    void welcomeMethodTest(){
        webTestClient
                .get()
                .uri("/v1/housingLoan/")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(String.class)
                .consumeWith(stringEntityExchangeResult -> {
                    String responseBody = stringEntityExchangeResult.getResponseBody();
                    assertEquals(responseBody,"Welcome to axis bank housing loan section");
                });
    }
    @Test
    void getAllLoanTest(){
        HousingLoanEvent housingLoan = new HousingLoanEvent("id", 2L, "address", "housing loan", 12, 2.3D, "applied", LoanEventType.NEW);
        when(housingLoanService.findAllHousingLoan())
                .thenReturn(Flux.just(housingLoan));
        Flux<HousingLoanEvent> responseBody = webTestClient
                .get()
                .uri("/v1/housingLoan/allHousingLoan")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .returnResult(HousingLoanEvent.class)
                .getResponseBody();
        StepVerifier.create(responseBody).expectNextCount(1).verifyComplete();
    }
    @Test
    void getHousingLoanByCustomerMobileNo(){
        HousingLoanEvent housingLoan = new HousingLoanEvent("id", 2L, "address", "housing loan", 12, 2.3D, "applied",LoanEventType.NEW);
        when(housingLoanService.findHousingLoanByMobileNo(isA(Long.class)))
                .thenReturn(Mono.just(housingLoan));
        webTestClient
                .get()
                .uri("/v1/housingLoan/customerMobileNo/2")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(HousingLoanEvent.class)
                .consumeWith(educationalLoanEntityExchangeResult -> {
                    HousingLoanEvent responseBody = educationalLoanEntityExchangeResult.getResponseBody();
                    assert responseBody.getCustomerMobileNo() == 2;
                });
    }
    @Test
    void deleteMethodTest(){
        when(housingLoanService.deleteHousingLoanById(isA(String.class)))
                .thenReturn(Mono.empty());
        webTestClient
                .delete()
                .uri("/v1/housingLoan/id")
                .exchange()
                .expectStatus()
                .isNoContent();
    }

}
