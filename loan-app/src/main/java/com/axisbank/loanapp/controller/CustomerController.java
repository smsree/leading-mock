package com.axisbank.loanapp.controller;

import com.axisbank.loanapp.domain.Customer;
import com.axisbank.loanapp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.swing.text.DefaultEditorKit;

@RestController
@RequestMapping("/v1/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/")
    public Mono<String> getWelcomeMsg(){
        return Mono.just("Welcome to Axis bank");
    }
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Customer> addNewCustomer(@RequestBody Customer customer){
        return customerService.addNewCutomer(customer);
    }

    @GetMapping("/all")
    public Flux<Customer> getAllCustomer(){
        return customerService.getAllCustomer();
    }
    @GetMapping("/{id}")
    public Mono<Customer> getByCustomerId(@PathVariable("id") String customerId){
        return customerService.getCustomerById(customerId);
    }
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Customer>> updateExistingCustomer(@PathVariable("id") String customerId, @RequestBody Customer customer){
        return customerService.updateCustomerById(customerId,customer)
                .map(customer1 -> {
                    return ResponseEntity.ok().body(customer1);
                })
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
    @GetMapping("/mobile/{mobileNo}")
    public Mono<ResponseEntity<Customer>> getCustomerInfoByMobileNo(@PathVariable("mobileNo") Long mobileNo){
        return customerService.getCustomerByMobileNo(mobileNo)
                .map(customer -> {
                    return ResponseEntity.ok().body(customer);
                })
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteCustomerById(@PathVariable("id") String id){
        return customerService.deleteCustomerById(id);
    }

}
