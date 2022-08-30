package com.axisbank.loanappofferservicewebclient.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerLoan {

    private Customer customer;
    private List<Loan> loanList;
}
