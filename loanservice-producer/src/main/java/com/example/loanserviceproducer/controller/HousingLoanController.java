package com.example.loanserviceproducer.controller;

import com.example.loanserviceproducer.domain.HousingLoanEvent;
import com.example.loanserviceproducer.domain.LoanEventType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.protocol.types.Field;
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
@RequestMapping("/v1/housingLoan")
@Slf4j
public class HousingLoanController {
    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    ObjectMapper objectMapper = new ObjectMapper();

    String topic = "housing-loan";
    @PostMapping("/")
    public ResponseEntity<String> postHousingLoanEventToBroker(@RequestBody @Valid HousingLoanEvent housingLoanEvent) throws JsonProcessingException {
        housingLoanEvent.setStatus("APPLIED");
        housingLoanEvent.setLoanEventType(LoanEventType.NEW);
        String key = housingLoanEvent.getHousingLoanId();
        String value = objectMapper.writeValueAsString(housingLoanEvent);
        ProducerRecord<String,String> record = new ProducerRecord<>(topic,key,value);
        ListenableFuture<SendResult<String, String>> send = kafkaTemplate.send(record);
        send.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Error sending the message (housing loan) and the exception is {}",ex.getMessage());
                try {
                    throw ex;
                } catch (Throwable e) {
                    log.error("Error in onFailue housing loan",e.getMessage());
                }
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("housing loan sent successfully with value {}",result.getProducerRecord().value());
            }
        });
        return ResponseEntity.status(HttpStatus.CREATED).body("housing loan Message sent successfully to the Broker");

    }
    @PutMapping("/")
    public ResponseEntity<String > updateHousingLoanEventToBroker(@RequestBody HousingLoanEvent housingLoanEvent) throws JsonProcessingException {
        if(housingLoanEvent.getHousingLoanId() == null){
            return new ResponseEntity<>("Need housing loan id to update",HttpStatus.BAD_REQUEST);
        }
        housingLoanEvent.setLoanEventType(LoanEventType.UPDATE);
        String key = housingLoanEvent.getHousingLoanId();
        String value = objectMapper.writeValueAsString(housingLoanEvent);
        ProducerRecord<String,String> record = new ProducerRecord<>(topic,key,value);
        ListenableFuture<SendResult<String, String>> send = kafkaTemplate.send(record);
        send.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Error sending the message (housing loan) and the exception is {}",ex.getMessage());
                try {
                    throw ex;
                } catch (Throwable e) {
                    log.error("Error in onFailue housing loan update",e.getMessage());
                }
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("housing loan updated successfully with value {}",result.getProducerRecord().value());
            }
        });
        return ResponseEntity.status(HttpStatus.OK).body("housing loan Message updated successfully to the Broker");

    }
}
