package com.example.loanserviceconsumer.controller;

import com.example.loanserviceconsumer.domain.BusinessLoanEvent;
import com.example.loanserviceconsumer.domain.LoanEventType;
import com.example.loanserviceconsumer.service.BusinessLoanEventControllerService;
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

@WebFluxTest(controllers = BusinessLoanEventController.class)
@AutoConfigureWebTestClient
public class BusinessLoanControllerUnitTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    BusinessLoanEventControllerService businessLoanServiceMock;

    @Test
    void welcomeTestMethod(){
        webTestClient
                .get()
                .uri("/v1/businessLoan/")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(String.class)
                .consumeWith(stringEntityExchangeResult -> {
                    String responseBody = stringEntityExchangeResult.getResponseBody();
                    assertEquals(responseBody,"Welcome to the axis bank business loan section");
                });
    }

    @Test
    void getAllLoanTest(){
        BusinessLoanEvent businessLoan = new BusinessLoanEvent("id", "farming", 2L, "mitra loan", 1000, 2.1D, "applied", LoanEventType.NEW);
        when(businessLoanServiceMock.getAllBusinessLoanAvailable())
                .thenReturn(Flux.just(businessLoan));
        Flux<BusinessLoanEvent> responseBody = webTestClient
                .get()
                .uri("/v1/businessLoan/allBuisnessLoan")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .returnResult(BusinessLoanEvent.class)
                .getResponseBody();
        StepVerifier.create(responseBody).expectNextCount(1).verifyComplete();
    }
    @Test
    void getLoanByCustomerMobileNo(){
        BusinessLoanEvent businessLoan = new BusinessLoanEvent("id", "farming", 2L, "mitra loan", 1000, 2.1D, "applied", LoanEventType.NEW);
        when(businessLoanServiceMock.findBusinessLoanByMobileNo(isA(Long.class)))
                .thenReturn(Mono.just(businessLoan));
        webTestClient
                .get()
                .uri("/v1/businessLoan/customerMobileNo/2")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(BusinessLoanEvent.class)
                .consumeWith(educationalLoanEntityExchangeResult -> {
                    BusinessLoanEvent responseBody = educationalLoanEntityExchangeResult.getResponseBody();
                    assert responseBody.getCustomerMobileNo() == 2;
                });
    }
    @Test
    void deleteMethodTest(){
        when(businessLoanServiceMock.deleteBusinessLoanByIdService(isA(String.class)))
                .thenReturn(Mono.empty());
        webTestClient
                .delete()
                .uri("/v1/businessLoan/id")
                .exchange()
                .expectStatus()
                .isNoContent();
    }
}
