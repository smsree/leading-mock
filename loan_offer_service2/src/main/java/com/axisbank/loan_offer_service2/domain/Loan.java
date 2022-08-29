package com.axisbank.loan_offer_service2.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Loan {
    @Id
    private String loanId;
    private Long customerMobileNo;
    private String loanName;
    private Integer loanamount;
    private Double rateOfInterest;
    private String status;
}
