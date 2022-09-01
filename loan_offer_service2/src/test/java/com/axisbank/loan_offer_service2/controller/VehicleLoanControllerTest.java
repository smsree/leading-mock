package com.axisbank.loan_offer_service2.controller;

import com.axisbank.loan_offer_service2.domain.EducationalLoan;
import com.axisbank.loan_offer_service2.domain.VehicleLoan;
import com.axisbank.loan_offer_service2.service.VehicleLoanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;
@WebFluxTest(controllers = VehicleLoanController.class)
@AutoConfigureWebTestClient
class VehicleLoanControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private VehicleLoanService vehicleLoanServiceMock;

    @Test
    void welcomeTestController(){
        webTestClient
                .get()
                .uri("/v1/vehicleLoan/")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(String.class)
                .consumeWith(stringEntityExchangeResult -> {
                    String responseBody = stringEntityExchangeResult.getResponseBody();
                    assertEquals(responseBody,"Welcome to axis bank vehicle loan section");
                });
    }

    @Test
    void addNewVehicleLoanTest(){
        VehicleLoan vehicle=new VehicleLoan("id",2L,"scooter","vehicle loan",10000,2.3D,"Applied");
        when(vehicleLoanServiceMock.addNewLoan(isA(VehicleLoan.class)))
                .thenReturn(Mono.just(vehicle));
        webTestClient
                .post()
                .uri("/v1/vehicleLoan/")
                .bodyValue(vehicle)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(VehicleLoan.class)
                .consumeWith(vehicleLoanEntityExchangeResult -> {
                    VehicleLoan responseBody = vehicleLoanEntityExchangeResult.getResponseBody();
                    assert responseBody.getVehicleName().equals("scooter");
                });
    }

    @Test
    void getAllLoanTest(){
        VehicleLoan vehicle=new VehicleLoan("id",2L,"scooter","vehicle loan",10000,2.3D,"Applied");
        when(vehicleLoanServiceMock.getAllVehicleLoan())
                .thenReturn(Flux.just(vehicle));
        Flux<VehicleLoan> responseBody = webTestClient
                .get()
                .uri("/v1/vehicleLoan/allVehicleLoan")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .returnResult(VehicleLoan.class)
                .getResponseBody();
        StepVerifier.create(responseBody).expectNextCount(1).verifyComplete();
    }
    @Test
    void getEducationalLoanByCustomerMobileNo(){
        VehicleLoan vehicle=new VehicleLoan("id",2L,"scooter","vehicle loan",10000,2.3D,"Applied");
        when(vehicleLoanServiceMock.getVehicleLoanByMobileNo(isA(Long.class)))
                .thenReturn(Mono.just(vehicle));
        webTestClient
                .get()
                .uri("/v1/vehicleLoan/customerMobileNo/2")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(VehicleLoan.class)
                .consumeWith(vehicleLoanEntityExchangeResult -> {
                    VehicleLoan responseBody = vehicleLoanEntityExchangeResult.getResponseBody();
                    assert responseBody.getCustomerMobileNo() == 2;
                });
    }
    @Test
    void getLoanById(){
        VehicleLoan vehicle=new VehicleLoan("id",2L,"scooter","vehicle loan",10000,2.3D,"Applied");
        when(vehicleLoanServiceMock.getVehicleLoanById(isA(String.class)))
                .thenReturn(Mono.just(vehicle));
        webTestClient
                .get()
                .uri("/v1/vehicleLoan/id")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(VehicleLoan.class)
                .consumeWith(loanEntityExchangeResult -> {
                    VehicleLoan responseBody = loanEntityExchangeResult.getResponseBody();
                    // System.out.println(responseBody);
                    assert responseBody.getVehicleLoanId().equals("id");
                });
    }
    @Test
    void updateLoanTest(){
        String id="id";
        VehicleLoan vehicle=new VehicleLoan("id",2L,"scooter","vehicle loan",10000,2.3D,"Applied");
        when(vehicleLoanServiceMock.updateVehcileLoanById(isA(String.class),isA(VehicleLoan.class)))
                .thenReturn(Mono.just(vehicle));
        webTestClient
                .put()
                .uri("/v1/vehicleLoan/id")
                .bodyValue(vehicle)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(VehicleLoan.class)
                .consumeWith(loanEntityExchangeResult -> {
                    VehicleLoan responseBody = loanEntityExchangeResult.getResponseBody();
                    assert responseBody!=null;
                });
    }
    @Test
    void deleteMethodTest(){
        when(vehicleLoanServiceMock.deleteVehicleLoanById(isA(String.class)))
                .thenReturn(Mono.empty());
        webTestClient
                .delete()
                .uri("/v1/vehicleLoan/id")
                .exchange()
                .expectStatus()
                .isNoContent();
    }
}