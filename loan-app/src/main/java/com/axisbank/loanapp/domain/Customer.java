package com.axisbank.loanapp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Customer {
    @Id
    private String customerId;
    private String name;
    private String work;
    private Long adharNo;
    private String panNo;
    private Long mobileNo;
    private String dob;
}
