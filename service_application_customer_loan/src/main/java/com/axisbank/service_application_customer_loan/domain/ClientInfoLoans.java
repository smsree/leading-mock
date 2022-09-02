package com.axisbank.service_application_customer_loan.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientInfoLoans {
    private Customer customer;
    private BusinessLoan businessLoan;
    private EducationalLoan educationalLoan;
    private HousingLoan housingLoan;
    private VehicalLoan vehicalLoan;
}
