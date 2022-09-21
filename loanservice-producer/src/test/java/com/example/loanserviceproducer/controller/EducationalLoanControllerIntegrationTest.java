package com.example.loanserviceproducer.controller;

import com.example.loanserviceproducer.domain.EducationalLoanEvent;
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
@EmbeddedKafka(topics = {"educational-loan"},partitions = 1)
@TestPropertySource(properties = {"spring.kafka.producer.bootstrap-servers=${spring.embedded.kafka.brokers}",
        "spring.kafka.admin.properties.bootstrap.servers=${spring.embedded.kafka.brokers}"})
public class EducationalLoanControllerIntegrationTest {
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
    void postEducationalEventTest(){
        EducationalLoanEvent educationalLoanEvent = new EducationalLoanEvent("abc",1L,"KSRCT","Educational loan",1000,3.2D,"APPLIED", LoanEventType.NEW);
        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", MediaType.APPLICATION_JSON.toString());
        HttpEntity<EducationalLoanEvent> request = new HttpEntity<>(educationalLoanEvent,headers);
        ResponseEntity<String> exchange = testRestTemplate.exchange("/v1/educationalLoan/", HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.CREATED,exchange.getStatusCode());

        ConsumerRecord<String, String> singleRecord = KafkaTestUtils.getSingleRecord(consumer, "educational-loan");
        String value = singleRecord.value();
        String expected = "{\"educationalLoanId\":\"abc\",\"customerMobileNo\":1,\"collegeName\":\"KSRCT\",\"loanName\":\"Educational loan\",\"loanamount\":1000,\"rateOfInterest\":3.2,\"status\":\"APPLIED\",\"loanEventType\":\"NEW\"}";
        assertEquals(expected,value);
    }
    @Test
    void putEndPointTest(){
        EducationalLoanEvent educationalLoanEvent = new EducationalLoanEvent("abc",1L,"KSRCT","Educational loan",1000,3.2D,"APPLIED", LoanEventType.NEW);
        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", MediaType.APPLICATION_JSON.toString());
        HttpEntity<EducationalLoanEvent> request = new HttpEntity<>(educationalLoanEvent,headers);
        ResponseEntity<String> exchange = testRestTemplate.exchange("/v1/educationalLoan/", HttpMethod.PUT, request, String.class);
        assertEquals(HttpStatus.OK,exchange.getStatusCode());
        ConsumerRecord<String, String> singleRecord = KafkaTestUtils.getSingleRecord(consumer, "educational-loan");
        String value = singleRecord.value();
        String expected = "{\"educationalLoanId\":\"abc\",\"customerMobileNo\":1,\"collegeName\":\"KSRCT\",\"loanName\":\"Educational loan\",\"loanamount\":1000,\"rateOfInterest\":3.2,\"status\":\"APPLIED\",\"loanEventType\":\"UPDATE\"}";
        assertEquals(expected,value);
    }
}
