package com.example.loanserviceproducer.controller;

import com.example.loanserviceproducer.domain.EducationalLoanEvent;
import com.example.loanserviceproducer.domain.LoanEventType;
import com.example.loanserviceproducer.domain.VehicleLoanEvent;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.TestPropertySource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment  = SpringBootTest.WebEnvironment.RANDOM_PORT)//to avoid port conflicts
@EmbeddedKafka(topics = {"vehicle-loan"},partitions = 1)
@TestPropertySource(properties = {"spring.kafka.producer.bootstrap-servers=${spring.embedded.kafka.brokers}",
        "spring.kafka.admin.properties.bootstrap.servers=${spring.embedded.kafka.brokers}"})
public class VehicleLoancontrollerIntegrationTest {
    @Autowired
    TestRestTemplate testRestTemplate;
    @Autowired
    EmbeddedKafkaBroker embeddedKafkaBroker;
    private Consumer<String,String> consumer;
    @BeforeEach
    void setUp(){
        Map<String,Object> configs  = new HashMap<>(KafkaTestUtils.consumerProps("group1","true",embeddedKafkaBroker));
        consumer = new DefaultKafkaConsumerFactory<>(configs,new StringDeserializer(),new StringDeserializer()).createConsumer();
        embeddedKafkaBroker.consumeFromAllEmbeddedTopics(consumer);
    }
    @AfterEach
    void tearDown(){
        consumer.close();
    }

    @Test
    void postVehicleLoanEventTest(){
        VehicleLoanEvent vehicleLoanEvent = new VehicleLoanEvent("abc",1L,"Bajaj","Vehicle loan",1000,3.2D,"APPLIED", LoanEventType.NEW);
        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", MediaType.APPLICATION_JSON.toString());
        HttpEntity<VehicleLoanEvent> request = new HttpEntity<>(vehicleLoanEvent,headers);
        ResponseEntity<String> exchange = testRestTemplate.exchange("/v1/vehicleLoan/", HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.CREATED,exchange.getStatusCode());
        ConsumerRecord<String, String> singleRecord = KafkaTestUtils.getSingleRecord(consumer, "vehicle-loan");
        String value = singleRecord.value();
        String expected = "{\"vehicleLoanId\":\"abc\",\"customerMobileNo\":1,\"vehicleName\":\"Bajaj\",\"loanName\":\"Vehicle loan\",\"loanamount\":1000,\"rateOfInterest\":3.2,\"status\":\"APPLIED\",\"loanEventType\":\"NEW\"}";
        assertEquals(expected,value);
    }
    @Test
    void updateVehicleLoanEventTest(){
        VehicleLoanEvent vehicleLoanEvent = new VehicleLoanEvent("abc",1L,"Bajaj","Vehicle loan",1000,3.2D,"APPLIED", LoanEventType.NEW);
        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", MediaType.APPLICATION_JSON.toString());
        HttpEntity<VehicleLoanEvent> request = new HttpEntity<>(vehicleLoanEvent,headers);
        ResponseEntity<String> exchange = testRestTemplate.exchange("/v1/vehicleLoan/", HttpMethod.PUT, request, String.class);
        assertEquals(HttpStatus.OK,exchange.getStatusCode());
        ConsumerRecord<String, String> singleRecord = KafkaTestUtils.getSingleRecord(consumer, "vehicle-loan");
        String value = singleRecord.value();
        String expected = "{\"vehicleLoanId\":\"abc\",\"customerMobileNo\":1,\"vehicleName\":\"Bajaj\",\"loanName\":\"Vehicle loan\",\"loanamount\":1000,\"rateOfInterest\":3.2,\"status\":\"APPLIED\",\"loanEventType\":\"UPDATE\"}";
        assertEquals(expected,value);
    }
}
