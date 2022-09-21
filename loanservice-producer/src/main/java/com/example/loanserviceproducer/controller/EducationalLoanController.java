package com.example.loanserviceproducer.controller;

import com.example.loanserviceproducer.domain.EducationalLoanEvent;
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
@Slf4j
@RequestMapping("/v1/educationalLoan")
public class EducationalLoanController {

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    ObjectMapper objectMapper = new ObjectMapper();

    String topic = "educational-loan";

    @PostMapping("/")
    public ResponseEntity<String> postNewEducationalLoan(@RequestBody @Valid EducationalLoanEvent educationalLoanEvent) throws JsonProcessingException {
        educationalLoanEvent.setStatus("APPLIED");
        educationalLoanEvent.setLoanEventType(LoanEventType.NEW);
        String value = objectMapper.writeValueAsString(educationalLoanEvent);
        String key = educationalLoanEvent.getEducationalLoanId();
        ProducerRecord<String,String> record = new ProducerRecord<>(topic,key,value);
        ListenableFuture<SendResult<String, String>> send = kafkaTemplate.send(record);
        send.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Error sending the message (educational loan) and the exception is {}",ex.getMessage());
                try {
                    throw ex;
                } catch (Throwable e) {
                    log.error("Error in onFailue educational loan",e.getMessage());
                }
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("Educational loan sent successfully with value {}",result.getProducerRecord().value());
            }
        });
        return ResponseEntity.status(HttpStatus.CREATED).body("Educational loan Message sent successfully to the Broker");
    }
    @PutMapping("/")
    public ResponseEntity<String> updateEducationalLoanEvent(@RequestBody EducationalLoanEvent educationalLoanEvent) throws JsonProcessingException {
        if(educationalLoanEvent.getEducationalLoanId() == null){
            return new ResponseEntity<String>("Educational loan id is needed to update",HttpStatus.BAD_REQUEST);
        }
        educationalLoanEvent.setLoanEventType(LoanEventType.UPDATE);
        String key = educationalLoanEvent.getEducationalLoanId();
        String value = objectMapper.writeValueAsString(educationalLoanEvent);
        ProducerRecord<String,String> record = new ProducerRecord<>(topic,key,value);
        ListenableFuture<SendResult<String, String>> send = kafkaTemplate.send(record);
        send.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Error sending the educational message and the exception is put {}",ex.getMessage());
                try {
                    throw ex;
                } catch (Throwable e) {
                    log.error("Error  put mapping in onFailue",e.getMessage());
                }
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("Educational Message sent successfully with value {}",result.getProducerRecord().value());
            }
        });
        return ResponseEntity.status(HttpStatus.OK).body("Educational loan Update Message sent successfully to the Broker");
    }
}
