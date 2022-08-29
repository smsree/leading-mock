package com.axisbank.loan_offer_service2.controller;

import com.axisbank.loan_offer_service2.domain.Loan;
import com.axisbank.loan_offer_service2.service.LoanService;
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

@WebFluxTest(controllers = LoanController.class)
@AutoConfigureWebTestClient
class LoanControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private LoanService loanService;

    @Test
    void welcomeTestMethod(){
        webTestClient
                .get()
                .uri("/v1/loan/")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(String.class)
                .consumeWith(stringEntityExchangeResult -> {
                    String responseBody = stringEntityExchangeResult.getResponseBody();
                    assertEquals(responseBody,"Welcome to axis bank loan section");
                });
    }

    @Test
    void addLoanMethodTest(){
        Loan loan = new Loan("id", 123L, "mitra", 100, 2.3D, "applied");
        when(loanService.addNewLoan(isA(Loan.class)))
                .thenReturn(Mono.just(loan));
        webTestClient
                .post()
                .uri("/v1/loan/")
                .bodyValue(loan)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(Loan.class)
                .consumeWith(loanEntityExchangeResult -> {
                    Loan responseBody = loanEntityExchangeResult.getResponseBody();
                    assert responseBody !=null;
                });
    }

    @Test
    void getAllLoanTest(){
        Loan loan = new Loan("id", 123L, "mitra", 100, 2.3D, "applied");
        when(loanService.getAllLoan())
                .thenReturn(Flux.just(loan));
        Flux<Loan> responseBody = webTestClient
                .get()
                .uri("/v1/loan/all")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .returnResult(Loan.class)
                .getResponseBody();
        StepVerifier.create(responseBody).expectNextCount(1).verifyComplete();
    }
    @Test
    void getLoanById(){
        Loan loan = new Loan("id", 123L, "mitra", 100, 2.3D, "applied");
        when(loanService.getLoanById(isA(String.class)))
                .thenReturn(Mono.just(loan));
        webTestClient
                .get()
                .uri("/v1/loan/id")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(Loan.class)
                .consumeWith(loanEntityExchangeResult -> {
                    Loan responseBody = loanEntityExchangeResult.getResponseBody();
                    assert responseBody.getLoanId().equals("id");
                });
    }

    @Test
    void updateLoanTest(){
        String id="id";
        Loan loan = new Loan("id", 123L, "mitra", 100, 2.3D, "applied");
        when(loanService.updateLoanById(id,loan))
                .thenReturn(Mono.just(loan));
        webTestClient
                .put()
                .uri("/v1/loan/id")
                .bodyValue(loan)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(Loan.class)
                .consumeWith(loanEntityExchangeResult -> {
                    Loan responseBody = loanEntityExchangeResult.getResponseBody();
                    assert responseBody!=null;
                });
    }

    @Test
    void deleteMethodTest(){
        when(loanService.deleteById(isA(String.class)))
                .thenReturn(Mono.empty());
        webTestClient
                .delete()
                .uri("/v1/loan/id")
                .exchange()
                .expectStatus()
                .isNoContent();
    }
}