package com.example.loanserviceconsumer.consumerService;

import com.example.loanserviceconsumer.domain.BusinessLoanEvent;
import com.example.loanserviceconsumer.repository.BusinessLoanRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class BusinessLoanKafkaConsumerService {

    @Autowired
    BusinessLoanRepository businessLoanRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    public void processBusinessLoanEventsPostAndPut(ConsumerRecord<String,String> consumerRecord) throws JsonProcessingException {
        BusinessLoanEvent businessLoanEvent = objectMapper.readValue(consumerRecord.value(), BusinessLoanEvent.class);
        log.info("Business loan : {}", businessLoanEvent);
        switch (businessLoanEvent.getLoanEventType()){
            case NEW:
                save(businessLoanEvent);
                break;
            case UPDATE:
                validate(businessLoanEvent);
                save(businessLoanEvent);
                break;
            default:
                log.info("invalid business loan event type");
        }
    }

    private void validate(BusinessLoanEvent businessLoanEvent) {
        //Mono<BusinessLoan> byId = businessLoanRepository.findById(businessLoan.getBusinessLoanId());

        //Mono<Boolean> validation = byId.filter(businessLoan1 -> businessLoan1.getBusinessLoanId() == businessLoan.getBusinessLoanId()).hasElement();
            try{
           Mono<Void> voidMono = businessLoanRepository.deleteById(businessLoanEvent.getBusinessLoanId());
           voidMono.subscribe((k)-> System.out.println("old record deleted"),(Throwable e)-> System.out.println(e.getMessage()),()-> System.out.println("validation completed"));
           log.info("Validation done {}", businessLoanEvent);}
            catch (Exception e){
                System.out.println(e.getMessage());
            }

    }

    private void save(BusinessLoanEvent businessLoanEvent) {
        Mono<BusinessLoanEvent> save = businessLoanRepository.save(businessLoanEvent);
        save.subscribe((businessLoanEvent1)-> System.out.println(businessLoanEvent1),(Throwable e)-> System.out.println(e.getMessage()),()-> System.out.println("Completed the task"));
        log.info("successfully saved business loan message to database:{}", businessLoanEvent);
    }
}
