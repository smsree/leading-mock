package com.axisbank.loanapp.service;

import com.axisbank.loanapp.domain.Customer;
import com.axisbank.loanapp.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    public Mono<Customer> addNewCutomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Flux<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    public Mono<Customer> getCustomerById(String customerId) {
        return customerRepository.findById(customerId);
    }

    public Mono<Customer> updateCustomerById(String customerId, Customer updatedCustomer) {

        Mono<Customer> customerMono = customerRepository.findById(customerId);
        return customerMono.flatMap(customer -> {
            customer.setName(updatedCustomer.getName());
            customer.setWork(updatedCustomer.getWork());
            customer.setAdharNo(updatedCustomer.getAdharNo());
            customer.setDob(updatedCustomer.getDob());
            customer.setPanNo(updatedCustomer.getPanNo());
            return customerRepository.save(customer);
        });
    }

    public Mono<Customer> getCustomerByMobileNo(Long mobileNo) {
        return customerRepository.findByMobileNo(mobileNo);
    }

    public Mono<Void> deleteCustomerById(String id) {
        return customerRepository.deleteById(id);
    }
}
