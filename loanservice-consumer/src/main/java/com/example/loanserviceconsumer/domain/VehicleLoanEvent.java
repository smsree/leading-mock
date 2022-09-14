package com.example.loanserviceconsumer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class VehicleLoanEvent {
    @Id
    private String vehicleLoanId;
    @NotNull
    @Positive(message = "customer mobile number should be a positive value")
    private Long customerMobileNo;
    @NotBlank(message = "vehicle name is need to proceed for the vehicle loan")
    private String vehicleName;
    @NotBlank(message = "loan name cannot be blank value")
    private String loanName;
    @NotNull
    @Positive(message = "loan amount should be a positive value")
    private Integer loanamount;
    @Min(value = 0L, message = "rating of interest should not be negative")
    private Double rateOfInterest;
    private String status;
    private LoanEventType loanEventType;
}
