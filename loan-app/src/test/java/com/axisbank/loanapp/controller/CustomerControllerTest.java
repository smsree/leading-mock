package com.axisbank.loanapp.controller;

import com.axisbank.loanapp.domain.Customer;
import com.axisbank.loanapp.service.CustomerService;
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
@WebFluxTest(controllers = CustomerController.class)
@AutoConfigureWebTestClient
class CustomerControllerTest {


    @Autowired
    private WebTestClient webTestClient;


    @MockBean
    private CustomerService customerServiceMock ;

    public CustomerService getCustomerServiceMock() {
        return customerServiceMock;
    }

    @Test
    void getWelcomeMsgTest(){
        webTestClient
                .get()
                .uri("/v1/customer/")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(String.class)
                .consumeWith(stringEntityExchangeResult -> {
                    String responseBody = stringEntityExchangeResult.getResponseBody();
                    assertEquals("Welcome to Axis bank",responseBody);
                });
    }
    @Test
    void getAllCustomerTest(){
        Customer customer = new Customer("abc", "name", "business", 909090909L, "asdfasfd",98789789L, "1999-09-08");
        when(customerServiceMock.getAllCustomer())
                .thenReturn(Flux.just(customer));
        Flux<Customer> responseBody = webTestClient
                .get()
                .uri("/v1/customer/all")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .returnResult(Customer.class)
                .getResponseBody();
        StepVerifier.create(responseBody).expectNextCount(1).verifyComplete();
    }
    @Test
    void getCusotmerByIdTest(){
        String id = "abc";
        Customer customer = new Customer("abc", "name", "business", 909090909L, "asdfasfd",98789789L, "1999-09-08");
        when(customerServiceMock.getCustomerById(id))
                .thenReturn(Mono.just(customer));
        webTestClient
                .get()
                .uri("/v1/customer/{id}",id)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(Customer.class)
                .consumeWith(customerEntityExchangeResult -> {
                    Customer responseBody = customerEntityExchangeResult.getResponseBody();
                    assert responseBody.getCustomerId().equals(id);
                });
    }

    @Test
    void addCustomerTest(){
        Customer customer = new Customer("abc", "name", "business", 909090909L, "asdfasfd",98789789L, "1999-09-08");
        when(customerServiceMock.addNewCutomer(isA(Customer.class)))
                .thenReturn(Mono.just(customer));

        webTestClient
                .post()
                .uri("/v1/customer/")
                .bodyValue(customer)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(Customer.class)
                .consumeWith(customerEntityExchangeResult -> {
                    Customer responseBody = customerEntityExchangeResult.getResponseBody();
                    assert responseBody!=null;
                });
    }

    @Test
    void updateExistingCustomerTest(){
        String id="abc";
        Customer customer = new Customer("abc", "name", "business", 909090909L, "asdfasfd",98789789L, "1999-09-08");
        when(customerServiceMock.updateCustomerById(id,customer))
                .thenReturn(Mono.just(customer));
        webTestClient
                .put()
                .uri("/v1/customer/{id}",id)
                .bodyValue(customer)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(Customer.class)
                .consumeWith(customerEntityExchangeResult -> {
                    Customer responseBody = customerEntityExchangeResult.getResponseBody();
                    assert responseBody!=null;
                });
    }

    @Test
    void deleteByIdTest(){
        String id="abc";
        Customer customer = new Customer("abc", "name", "business", 909090909L, "asdfasfd",98789789L, "1999-09-08");
        when(customerServiceMock.deleteCustomerById(id))
                .thenReturn(Mono.empty());
        webTestClient
                .delete()
                .uri("/v1/customer/{id}",id)
                .exchange()
                .expectStatus()
                .isNoContent();
    }


}