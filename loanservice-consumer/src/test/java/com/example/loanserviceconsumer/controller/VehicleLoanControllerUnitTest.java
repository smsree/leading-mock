package com.example.loanserviceconsumer.controller;

import com.example.loanserviceconsumer.domain.LoanEventType;
import com.example.loanserviceconsumer.domain.VehicleLoanEvent;
import com.example.loanserviceconsumer.service.VehicleLoanEventControllerService;
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

@WebFluxTest(controllers = VehicleLoanEventController.class)
@AutoConfigureWebTestClient
public class VehicleLoanControllerUnitTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    VehicleLoanEventControllerService vehicleLoanServiceMock;

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
    void getAllLoanTest(){
        VehicleLoanEvent vehicle=new VehicleLoanEvent("id",2L,"scooter","vehicle loan",10000,2.3D,"Applied", LoanEventType.NEW);
        when(vehicleLoanServiceMock.getAllVehicleLoan())
                .thenReturn(Flux.just(vehicle));
        Flux<VehicleLoanEvent> responseBody = webTestClient
                .get()
                .uri("/v1/vehicleLoan/allVehicleLoan")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .returnResult(VehicleLoanEvent.class)
                .getResponseBody();
        StepVerifier.create(responseBody).expectNextCount(1).verifyComplete();
    }
    @Test
    void getEducationalLoanByCustomerMobileNo(){
        VehicleLoanEvent vehicle=new VehicleLoanEvent("id",2L,"scooter","vehicle loan",10000,2.3D,"Applied",LoanEventType.NEW);
        when(vehicleLoanServiceMock.getVehicleLoanByMobileNo(isA(Long.class)))
                .thenReturn(Mono.just(vehicle));
        webTestClient
                .get()
                .uri("/v1/vehicleLoan/customerMobileNo/2")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(VehicleLoanEvent.class)
                .consumeWith(vehicleLoanEntityExchangeResult -> {
                    VehicleLoanEvent responseBody = vehicleLoanEntityExchangeResult.getResponseBody();
                    assert responseBody.getCustomerMobileNo() == 2;
                });
    }
    @Test
    void getLoanById(){
        VehicleLoanEvent vehicle=new VehicleLoanEvent("id",2L,"scooter","vehicle loan",10000,2.3D,"Applied",LoanEventType.NEW);
        when(vehicleLoanServiceMock.getVehicleLoanById(isA(String.class)))
                .thenReturn(Mono.just(vehicle));
        webTestClient
                .get()
                .uri("/v1/vehicleLoan/id")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(VehicleLoanEvent.class)
                .consumeWith(loanEntityExchangeResult -> {
                    VehicleLoanEvent responseBody = loanEntityExchangeResult.getResponseBody();
                    // System.out.println(responseBody);
                    assert responseBody.getVehicleLoanId().equals("id");
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
