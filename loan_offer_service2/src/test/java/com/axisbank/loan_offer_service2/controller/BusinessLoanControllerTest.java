package com.axisbank.loan_offer_service2.controller;

import com.axisbank.loan_offer_service2.domain.BusinessLoan;
import com.axisbank.loan_offer_service2.domain.EducationalLoan;
import com.axisbank.loan_offer_service2.service.BusinessLoanService;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = BusinessLoanController.class)
@AutoConfigureWebTestClient
class BusinessLoanControllerTest {

    ///v1/businessLoan
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private BusinessLoanService businessLoanServiceMock;

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
        BusinessLoan businessLoan = new BusinessLoan("id", "farming", 2L, "mitra loan", 1000, 2.1D, "applied");
        when(businessLoanServiceMock.getAllBusinessLoanAvailable())
                .thenReturn(Flux.just(businessLoan));
        Flux<BusinessLoan> responseBody = webTestClient
                .get()
                .uri("/v1/businessLoan/allBuisnessLoan")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .returnResult(BusinessLoan.class)
                .getResponseBody();
        StepVerifier.create(responseBody).expectNextCount(1).verifyComplete();
    }

    @Test
    void addLoanMethodTest(){
        BusinessLoan businessLoan = new BusinessLoan("id", "farming", 2L, "mitra loan", 1000, 2.1D, "applied");
        when(businessLoanServiceMock.addNewBusinessLoan(isA(BusinessLoan.class)))
                .thenReturn(Mono.just(businessLoan));
        webTestClient
                .post()
                .uri("/v1/businessLoan/")
                .bodyValue(businessLoan)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(BusinessLoan.class)
                .consumeWith(loanEntityExchangeResult -> {
                    BusinessLoan responseBody = loanEntityExchangeResult.getResponseBody();
                    assert responseBody !=null;
                });
    }

    @Test
    void getLoanByCustomerMobileNo(){
        BusinessLoan businessLoan = new BusinessLoan("id", "farming", 2L, "mitra loan", 1000, 2.1D, "applied");
        when(businessLoanServiceMock.findBusinessLoanByMobileNo(isA(Long.class)))
                .thenReturn(Mono.just(businessLoan));
        webTestClient
                .get()
                .uri("/v1/businessLoan/customerMobileNo/2")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(BusinessLoan.class)
                .consumeWith(educationalLoanEntityExchangeResult -> {
                    BusinessLoan responseBody = educationalLoanEntityExchangeResult.getResponseBody();
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