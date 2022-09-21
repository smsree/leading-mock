package com.example.loanserviceconsumer.consumerService;

import com.example.loanserviceconsumer.domain.EducationalLoanEvent;
import com.example.loanserviceconsumer.repository.EducationalLoanRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class EducationalLoanConsumerService {
    @Autowired
    EducationalLoanRepository educationalLoanRepository;

    ObjectMapper objectMapper = new ObjectMapper();


    public void processEducationalLoanEventsPostAndPut(ConsumerRecord<String, String> consumerRecord) throws JsonProcessingException {
        EducationalLoanEvent educationalLoanEvent = objectMapper.readValue(consumerRecord.value(), EducationalLoanEvent.class);
        log.info("Educational loan : {}", educationalLoanEvent);
        switch (educationalLoanEvent.getLoanEventType())
        {
            case NEW:
                save(educationalLoanEvent);
                break;
            case UPDATE:
                validate(educationalLoanEvent);
                save(educationalLoanEvent);
                break;
            default:
                log.info("invalid educational loan event type");
        }
    }

    private void validate(EducationalLoanEvent educationalLoanEvent) {
        try {
            Mono<Void> voidMono = educationalLoanRepository.deleteById(educationalLoanEvent.getEducationalLoanId());
            voidMono.subscribe((k) -> System.out.println("old record deleted"), (Throwable e) -> System.out.println(e.getMessage()), () -> System.out.println("validation completed"));
            log.info("Validation done {}", educationalLoanEvent);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        log.info("Validation done for educational loan {}",educationalLoanEvent);
    }

    private void save(EducationalLoanEvent educationalLoanEvent) {
        Mono<EducationalLoanEvent> save = educationalLoanRepository.save(educationalLoanEvent);
        save.subscribe(educationalLoanEvent1 -> System.out.println(educationalLoanEvent1),(Throwable e)-> System.out.println(e.getMessage()),()-> System.out.println("Completed the task of educational loan event"));
        log.info("successfully saved educational loan message to database:{}", educationalLoanEvent);
    }
}
