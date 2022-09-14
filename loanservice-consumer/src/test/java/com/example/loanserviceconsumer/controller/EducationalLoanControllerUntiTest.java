package com.example.loanserviceconsumer.controller;

import com.example.loanserviceconsumer.domain.EducationalLoanEvent;
import com.example.loanserviceconsumer.domain.LoanEventType;
import com.example.loanserviceconsumer.service.EducationalLoanControllerEventService;
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

@WebFluxTest(controllers = EducationalLoanEventController.class)
@AutoConfigureWebTestClient
public class EducationalLoanControllerUntiTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    EducationalLoanControllerEventService educationalLoanServiceMock;

    @Test
    void welcomeTestMethod(){
        webTestClient
                .get()
                .uri("/v1/educationalLoan/")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(String.class)
                .consumeWith(stringEntityExchangeResult -> {
                    String responseBody = stringEntityExchangeResult.getResponseBody();
                    assertEquals(responseBody,"Welcome to the axis bank educational loan section");
                });
    }
    @Test
    void getAllLoanTest(){
        EducationalLoanEvent educationalLoan = new EducationalLoanEvent("id", 2L, "abc", "abc", 10, 2.4, "applied", LoanEventType.NEW);
        when(educationalLoanServiceMock.findAllEducationalLoan())
                .thenReturn(Flux.just(educationalLoan));
        Flux<EducationalLoanEvent> responseBody = webTestClient
                .get()
                .uri("/v1/educationalLoan/allEducationalLoan")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .returnResult(EducationalLoanEvent.class)
                .getResponseBody();
        StepVerifier.create(responseBody).expectNextCount(1).verifyComplete();
    }
    @Test
    void getEducationalLoanByCustomerMobileNo(){
        EducationalLoanEvent educationalLoan = new EducationalLoanEvent("id", 2L, "abc", "abc", 10, 2.4, "applied",LoanEventType.NEW);
        when(educationalLoanServiceMock.findEducationalLoanByMobileNo(isA(Long.class)))
                .thenReturn(Mono.just(educationalLoan));
        webTestClient
                .get()
                .uri("/v1/educationalLoan/customerMobileNo/2")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(EducationalLoanEvent.class)
                .consumeWith(educationalLoanEntityExchangeResult -> {
                    EducationalLoanEvent responseBody = educationalLoanEntityExchangeResult.getResponseBody();
                    assert responseBody.getCustomerMobileNo() == 2;
                });
    }
    @Test
    void getLoanById(){
        EducationalLoanEvent educationalLoan = new EducationalLoanEvent("id", 2L, "abc", "abc", 10, 2.4, "applied",LoanEventType.NEW);
        when(educationalLoanServiceMock.findEducationalLoanById(isA(String.class)))
                .thenReturn(Mono.just(educationalLoan));
        webTestClient
                .get()
                .uri("/v1/educationalLoan/id")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(EducationalLoanEvent.class)
                .consumeWith(loanEntityExchangeResult -> {
                    EducationalLoanEvent responseBody = loanEntityExchangeResult.getResponseBody();
                    // System.out.println(responseBody);
                    assert responseBody.getEducationalLoanId().equals("id");
                });
    }
    @Test
    void deleteMethodTest(){
        when(educationalLoanServiceMock.deleteEducationalLoanById(isA(String.class)))
                .thenReturn(Mono.empty());
        webTestClient
                .delete()
                .uri("/v1/educationalLoan/id")
                .exchange()
                .expectStatus()
                .isNoContent();
    }
}
