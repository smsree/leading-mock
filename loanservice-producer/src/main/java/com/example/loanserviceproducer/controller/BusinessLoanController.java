package com.example.loanserviceproducer.controller;

import com.example.loanserviceproducer.domain.BusinessLoanEvent;
import com.example.loanserviceproducer.domain.LoanEventType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/businessLoan")
@Slf4j
public class BusinessLoanController {
    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    ObjectMapper objectMapper = new ObjectMapper();

    String topic = "business-loan";
    String onSuccess1 = null;
    @PostMapping("/")
    public ResponseEntity<String> postEventToKafka(@RequestBody @Valid BusinessLoanEvent businessLoanEvent) throws JsonProcessingException {
        businessLoanEvent.setStatus("APPLIED");
        businessLoanEvent.setLoanEventType(LoanEventType.NEW);
        String key = businessLoanEvent.getBusinessLoanId();
        String value = objectMapper.writeValueAsString(businessLoanEvent);
        ProducerRecord<String,String> record = new ProducerRecord<>(topic,key,value);
        ListenableFuture<SendResult<String, String>> send = kafkaTemplate.send(record);

        send.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Error sending the message (Business loan) and the exception is {}",ex.getMessage());
                try {
                    throw ex;
                } catch (Throwable e) {
                    log.error("Error in onFailue Business loan",e.getMessage());
                }
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("Business loan sent successfully with value {}",result.getProducerRecord().value());
            }
        });



        return ResponseEntity.status(HttpStatus.CREATED).body("Business loan Message sent successfully to the Broker");

    }
    @PutMapping("/")
    public ResponseEntity<String> updateExistingEventViaKafka(@RequestBody BusinessLoanEvent businessLoanEvent) throws JsonProcessingException {
        if(businessLoanEvent.getBusinessLoanId() == null){
            return new ResponseEntity<String>("Business loan id is needed to update",HttpStatus.BAD_REQUEST);
        }
        businessLoanEvent.setLoanEventType(LoanEventType.UPDATE);
        String key = businessLoanEvent.getBusinessLoanId();
        String value = objectMapper.writeValueAsString(businessLoanEvent);
        ProducerRecord<String,String> record = new ProducerRecord<>(topic,key,value);
        ListenableFuture<SendResult<String, String>> send = kafkaTemplate.send(record);
        send.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Error sending the message and the exception is {}",ex.getMessage());
                try {
                    throw ex;
                } catch (Throwable e) {
                    log.error("Error in onFailue",e.getMessage());
                }
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("Message sent successfully with value {}",result.getProducerRecord().value());
            }
        });
        return ResponseEntity.status(HttpStatus.OK).body("Update Message sent successfully to the Broker");
    }


}
