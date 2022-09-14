package com.example.loanserviceproducer.controller;

import com.example.loanserviceproducer.domain.LoanEventType;
import com.example.loanserviceproducer.domain.VehicleLoanEvent;
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
@RequestMapping("/v1/vehicleLoan")
@Slf4j
public class VehicleLoancontroller {
    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    ObjectMapper objectMapper = new ObjectMapper();

    String topic = "vehicle-loan";

    @PostMapping("/")
    public ResponseEntity<String> postVehcileLoanEventToBroker(@RequestBody @Valid VehicleLoanEvent vehicleLoanEvent) throws JsonProcessingException {
        vehicleLoanEvent.setStatus("APPLIED");
        vehicleLoanEvent.setLoanEventType(LoanEventType.NEW);
        String value = objectMapper.writeValueAsString(vehicleLoanEvent);
        String key = vehicleLoanEvent.getVehicleLoanId();
        ProducerRecord<String,String> record = new ProducerRecord<>(topic,key,value);
        ListenableFuture<SendResult<String, String>> send = kafkaTemplate.send(record);
        send.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Error sending the message (vehicle loan) and the exception is {}",ex.getMessage());
                try {
                    throw ex;
                } catch (Throwable e) {
                    log.error("Error in onFailue vehicle loan",e.getMessage());
                }
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("vehicle loan sent successfully with value {}",result.getProducerRecord().value());
            }
        });
        return ResponseEntity.status(HttpStatus.CREATED).body("vehicle loan Message sent successfully to the Broker");

    }
    @PutMapping("/")
    public ResponseEntity<String > updateVehicelLoan(@RequestBody VehicleLoanEvent vehicleLoanEvent) throws JsonProcessingException {
        if(vehicleLoanEvent.getVehicleLoanId() == null){
            return new ResponseEntity<>("Vehicle loan id is need to update",HttpStatus.BAD_REQUEST);
        }
        vehicleLoanEvent.setLoanEventType(LoanEventType.UPDATE);
        String key = vehicleLoanEvent.getVehicleLoanId();
        String value = objectMapper.writeValueAsString(vehicleLoanEvent);
        ProducerRecord<String,String> record = new ProducerRecord<>(topic,key,value);
        ListenableFuture<SendResult<String, String>> send = kafkaTemplate.send(record);
        send.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Error sending the vehicle message and the exception is put {}",ex.getMessage());
                try {
                    throw ex;
                } catch (Throwable e) {
                    log.error("Error  put mapping in onFailue",e.getMessage());
                }
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("Vehicle loan Message sent successfully with value {}",result.getProducerRecord().value());
            }
        });
        return ResponseEntity.status(HttpStatus.OK).body("vehicle loan Update Message sent successfully to the Broker");

    }
}
