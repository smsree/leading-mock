package com.axisbank.loanappofferservicewebclient.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Customer {

    private String customerId;
    @NotBlank(message = "customer name must not be null")
    private String name;
    @NotBlank(message = "customer work should not be null if student please mention student in place of work")
    private String work;
    @NotNull
    @Positive(message = "adhar number must be a positive value")
    private Long adharNo;
    private String panNo;
    @NotNull
    @Positive(message = "phone number is needed to create an account in your name it should be a positive value")
    private Long mobileNo;
    private String dob;
}
