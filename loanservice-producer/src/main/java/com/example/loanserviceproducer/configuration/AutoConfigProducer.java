package com.example.loanserviceproducer.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
@Configuration
public class AutoConfigProducer {
    @Bean
    public NewTopic businessEvent(){
        return  TopicBuilder.name("business-loan")
                .partitions(1)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic educationalEvent(){
        return TopicBuilder
                .name("educational-loan")
                .partitions(1)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic vehicleLoanEvent(){
        return TopicBuilder
                .name("vehicle-loan")
                .partitions(1)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic housingLoanEvent(){
        return TopicBuilder
                .name("housing-loan")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
