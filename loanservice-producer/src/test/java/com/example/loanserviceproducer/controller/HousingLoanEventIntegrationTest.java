package com.example.loanserviceproducer.controller;

import com.example.loanserviceproducer.domain.HousingLoanEvent;
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
@EmbeddedKafka(topics = {"housing-loan"},partitions = 1)
@TestPropertySource(properties = {"spring.kafka.producer.bootstrap-servers=${spring.embedded.kafka.brokers}",
        "spring.kafka.admin.properties.bootstrap.servers=${spring.embedded.kafka.brokers}"})
public class HousingLoanEventIntegrationTest {
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
    void postHousingLoanEventToBroker(){
        HousingLoanEvent housingLoanEvent = new HousingLoanEvent("id",1L,"add","housing loan",100,2.2D,"APPLIED", LoanEventType.NEW);
        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", MediaType.APPLICATION_JSON.toString());
        HttpEntity<HousingLoanEvent> request = new HttpEntity<>(housingLoanEvent,headers);
        ResponseEntity<String> exchange = testRestTemplate.exchange("/v1/housingLoan/", HttpMethod.POST, request, String.class);
        assertEquals(HttpStatus.CREATED,exchange.getStatusCode());
        ConsumerRecord<String, String> singleRecord = KafkaTestUtils.getSingleRecord(consumer, "housing-loan");
        String value = singleRecord.value();
        String expected="{\"housingLoanId\":\"id\",\"customerMobileNo\":1,\"address\":\"add\",\"loanName\":\"housing loan\",\"loanamount\":100,\"rateOfInterest\":2.2,\"status\":\"APPLIED\",\"loanEventType\":\"NEW\"}";
        assertEquals(expected,value);
    }
    @Test
    void postHousingLoanEventTest(){
        HousingLoanEvent housingLoanEvent = new HousingLoanEvent("id",1L,"add","housing loan",100,2.2D,"APPLIED", LoanEventType.NEW);
        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", MediaType.APPLICATION_JSON.toString());
        HttpEntity<HousingLoanEvent> request = new HttpEntity<>(housingLoanEvent,headers);
        ResponseEntity<String> exchange = testRestTemplate.exchange("/v1/housingLoan/", HttpMethod.PUT, request, String.class);
        assertEquals(HttpStatus.OK,exchange.getStatusCode());
        ConsumerRecord<String, String> singleRecord = KafkaTestUtils.getSingleRecord(consumer, "housing-loan");
        String value = singleRecord.value();
        String expected="{\"housingLoanId\":\"id\",\"customerMobileNo\":1,\"address\":\"add\",\"loanName\":\"housing loan\",\"loanamount\":100,\"rateOfInterest\":2.2,\"status\":\"APPLIED\",\"loanEventType\":\"UPDATE\"}";
        assertEquals(expected,value);
    }
}
