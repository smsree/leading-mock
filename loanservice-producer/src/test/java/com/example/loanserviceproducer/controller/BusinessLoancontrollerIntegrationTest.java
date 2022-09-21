package com.example.loanserviceproducer.controller;

import com.example.loanserviceproducer.domain.BusinessLoanEvent;
import com.example.loanserviceproducer.domain.LoanEventType;

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
@EmbeddedKafka(topics = {"business-loan"},partitions = 1)
@TestPropertySource(properties = {"spring.kafka.producer.bootstrap-servers=${spring.embedded.kafka.brokers}",
        "spring.kafka.admin.properties.bootstrap.servers=${spring.embedded.kafka.brokers}"})
public class BusinessLoancontrollerIntegrationTest {

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
    void postBusinessLoanEvent(){
        BusinessLoanEvent businessLoanEvent  = new  BusinessLoanEvent(null,"golde business",10L,"businessLoan",1000,4.3D,"applied", LoanEventType.NEW);
        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", MediaType.APPLICATION_JSON.toString());
        HttpEntity<BusinessLoanEvent> request = new HttpEntity<>(businessLoanEvent,headers);
        ResponseEntity<String> exchange = testRestTemplate.exchange("/v1/businessLoan/", HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.CREATED,exchange.getStatusCode());
        ConsumerRecord<String, String> singleRecord = KafkaTestUtils.getSingleRecord(consumer, "business-loan");
        String value = singleRecord.value();
        String expected = "{\"businessLoanId\":null,\"businessName\":\"golde business\",\"customerMobileNo\":10,\"loanName\":\"businessLoan\",\"loanamount\":1000,\"rateOfInterest\":4.3,\"status\":\"APPLIED\",\"loanEventType\":\"NEW\"}";
        assertEquals(expected,value);
    }

    @Test
    void putBusinessLoanEvent(){
        BusinessLoanEvent businessLoanEvent  = new  BusinessLoanEvent("abc","golde business",10L,"businessLoan",1000,4.3D,"APPLIED", LoanEventType.NEW);
        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", MediaType.APPLICATION_JSON.toString());
        HttpEntity<BusinessLoanEvent> request = new HttpEntity<>(businessLoanEvent,headers);
        ResponseEntity<String> exchange = testRestTemplate.exchange("/v1/businessLoan/", HttpMethod.PUT, request, String.class);
        assertEquals(HttpStatus.OK,exchange.getStatusCode());
        ConsumerRecord<String, String> singleRecord = KafkaTestUtils.getSingleRecord(consumer, "business-loan");
        String value = singleRecord.value();
        String expected = "{\"businessLoanId\":\"abc\",\"businessName\":\"golde business\",\"customerMobileNo\":10,\"loanName\":\"businessLoan\",\"loanamount\":1000,\"rateOfInterest\":4.3,\"status\":\"APPLIED\",\"loanEventType\":\"UPDATE\"}";
        assertEquals(expected,value);
    }
}
