package com.axisbank.loan_offer_service2.controller;

import com.axisbank.loan_offer_service2.domain.EducationalLoan;
import com.axisbank.loan_offer_service2.domain.Loan;
import com.axisbank.loan_offer_service2.service.EducationalLoanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = EducatinalLoanController.class)
@AutoConfigureWebTestClient
class EducatinalLoanControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private EducationalLoanService educationalLoanServiceMock;


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
        EducationalLoan educationalLoan = new EducationalLoan("id", 2L, "abc", "abc", 10, 2.4, "applied");
        when(educationalLoanServiceMock.findAllEducationalLoan())
                .thenReturn(Flux.just(educationalLoan));
        Flux<EducationalLoan> responseBody = webTestClient
                .get()
                .uri("/v1/educationalLoan/allEducationalLoan")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .returnResult(EducationalLoan.class)
                .getResponseBody();
        StepVerifier.create(responseBody).expectNextCount(1).verifyComplete();
    }

    @Test
    void addLoanMethodTest(){
        EducationalLoan educationalLoan = new EducationalLoan("id", 2L, "abc", "abc", 10, 2.4, "applied");
        when(educationalLoanServiceMock.addNewEducationalLoan(isA(EducationalLoan.class)))
                .thenReturn(Mono.just(educationalLoan));
        webTestClient
                .post()
                .uri("/v1/educationalLoan/")
                .bodyValue(educationalLoan)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(EducationalLoan.class)
                .consumeWith(loanEntityExchangeResult -> {
                    EducationalLoan responseBody = loanEntityExchangeResult.getResponseBody();
                    assert responseBody !=null;
                });
    }
    @Test
    void getEducationalLoanByCustomerMobileNo(){
        EducationalLoan educationalLoan = new EducationalLoan("id", 2L, "abc", "abc", 10, 2.4, "applied");
        when(educationalLoanServiceMock.findEducationalLoanByMobileNo(isA(Long.class)))
                .thenReturn(Mono.just(educationalLoan));
        webTestClient
                .get()
                .uri("/v1/educationalLoan/customerMobileNo/2")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(EducationalLoan.class)
                .consumeWith(educationalLoanEntityExchangeResult -> {
                    EducationalLoan responseBody = educationalLoanEntityExchangeResult.getResponseBody();
                    assert responseBody.getCustomerMobileNo() == 2;
                });
    }
    @Test
    void getLoanById(){
        EducationalLoan educationalLoan = new EducationalLoan("id", 2L, "abc", "abc", 10, 2.4, "applied");
        when(educationalLoanServiceMock.findEducationalLoanById(isA(String.class)))
                .thenReturn(Mono.just(educationalLoan));
        webTestClient
                .get()
                .uri("/v1/educationalLoan/id")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(EducationalLoan.class)
                .consumeWith(loanEntityExchangeResult -> {
                    EducationalLoan responseBody = loanEntityExchangeResult.getResponseBody();
                   // System.out.println(responseBody);
                    assert responseBody.getEducationalLoanId().equals("id");
                });
    }
    @Test
    void updateLoanTest(){
        String id="id";
        EducationalLoan educationalLoan = new EducationalLoan("id", 2L, "abc", "abc", 10, 2.4, "applied");
        when(educationalLoanServiceMock.updateExistingLoanById(isA(String.class),isA(EducationalLoan.class)))
                .thenReturn(Mono.just(educationalLoan));
        webTestClient
                .put()
                .uri("/v1/educationalLoan/id")
                .bodyValue(educationalLoan)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(EducationalLoan.class)
                .consumeWith(loanEntityExchangeResult -> {
                    EducationalLoan responseBody = loanEntityExchangeResult.getResponseBody();
                    assert responseBody!=null;
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