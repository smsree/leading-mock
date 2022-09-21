package com.example.loanserviceconsumer.consumer;

import com.example.loanserviceconsumer.consumerService.BusinessLoanKafkaConsumerService;
import com.example.loanserviceconsumer.consumerService.EducationalLoanConsumerService;
import com.example.loanserviceconsumer.consumerService.HousingLoanConsumerService;
import com.example.loanserviceconsumer.consumerService.VehicleLoanConsumerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LoanEventConsumer {
    @Autowired
    BusinessLoanKafkaConsumerService businessLoanKafkaConsumerService;
    @Autowired
    EducationalLoanConsumerService educationalLoanConsumerService;

    @Autowired
    VehicleLoanConsumerService vehicleLoanConsumerService;

    @Autowired
    HousingLoanConsumerService housingLoanConsumerService;
    @KafkaListener(topics = {"business-loan"})
    public void onMessage(ConsumerRecord<String,String> consumerRecord)throws JsonProcessingException {
        log.info("business loan consumer:{}",consumerRecord.value());
        businessLoanKafkaConsumerService.processBusinessLoanEventsPostAndPut(consumerRecord);
    }
    @KafkaListener(topics = {"educational-loan"})
    public void onMessageEducational(ConsumerRecord<String,String> consumerRecord) throws JsonProcessingException {
        log.info("educational loan consumer:{}",consumerRecord.value());
        educationalLoanConsumerService.processEducationalLoanEventsPostAndPut(consumerRecord);
    }
    @KafkaListener(topics = {"vehicle-loan"})
    public void onMessageVehicle(ConsumerRecord<String,String> consumerRecord) throws JsonProcessingException{
        log.info("Vehicle loan consumer {}",consumerRecord.value());
        vehicleLoanConsumerService.processVehicleLoanEventsPostAndPut(consumerRecord);
    }
    @KafkaListener(topics = {"housing-loan"})
    public void onMessageHousing(ConsumerRecord<String,String> consumerRecord) throws JsonProcessingException{
        log.info("housing loan {}",consumerRecord.value());
        housingLoanConsumerService.processHousingLoanEventPostAndPut(consumerRecord);
    }
}
