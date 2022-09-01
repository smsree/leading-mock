package com.axisbank.loan_offer_service2.controller;

import com.axisbank.loan_offer_service2.domain.EducationalLoan;
import com.axisbank.loan_offer_service2.domain.HousingLoan;
import com.axisbank.loan_offer_service2.service.HousingLoanService;
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

@WebFluxTest(controllers = HousingLoanController.class)
@AutoConfigureWebTestClient
class HousingLoanControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private HousingLoanService housingLoanService;

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
        HousingLoan housingLoan = new HousingLoan("id", 2L, "address", "housing loan", 12, 2.3D, "applied");
        when(housingLoanService.findAllHousingLoan())
                .thenReturn(Flux.just(housingLoan));
        Flux<HousingLoan> responseBody = webTestClient
                .get()
                .uri("/v1/housingLoan/allHousingLoan")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .returnResult(HousingLoan.class)
                .getResponseBody();
        StepVerifier.create(responseBody).expectNextCount(1).verifyComplete();
    }
    @Test
    void getHousingLoanByCustomerMobileNo(){
        HousingLoan housingLoan = new HousingLoan("id", 2L, "address", "housing loan", 12, 2.3D, "applied");
        when(housingLoanService.findHousingLoanByMobileNo(isA(Long.class)))
                .thenReturn(Mono.just(housingLoan));
        webTestClient
                .get()
                .uri("/v1/housingLoan/customerMobileNo/2")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(HousingLoan.class)
                .consumeWith(educationalLoanEntityExchangeResult -> {
                    HousingLoan responseBody = educationalLoanEntityExchangeResult.getResponseBody();
                    assert responseBody.getCustomerMobileNo() == 2;
                });
    }
    @Test
    void updateLoanTest(){
        String id="id";
        HousingLoan housingLoan = new HousingLoan("id", 2L, "address", "housing loan", 12, 2.3D, "applied");
        when(housingLoanService.updateHousingLoanById(isA(String.class),isA(HousingLoan.class)))
                .thenReturn(Mono.just(housingLoan));
        webTestClient
                .put()
                .uri("/v1/housingLoan/id")
                .bodyValue(housingLoan)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(HousingLoan.class)
                .consumeWith(loanEntityExchangeResult -> {
                    HousingLoan responseBody = loanEntityExchangeResult.getResponseBody();
                    assert responseBody!=null;
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