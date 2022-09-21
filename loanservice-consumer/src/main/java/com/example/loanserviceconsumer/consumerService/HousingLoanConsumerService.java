package com.example.loanserviceconsumer.consumerService;

import com.example.loanserviceconsumer.domain.HousingLoanEvent;
import com.example.loanserviceconsumer.repository.HousingLoanRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class HousingLoanConsumerService {
    @Autowired
    HousingLoanRepository housingLoanRepository;
    ObjectMapper objectMapper = new ObjectMapper();
    public void processHousingLoanEventPostAndPut(ConsumerRecord<String, String> consumerRecord) throws JsonProcessingException {
        HousingLoanEvent housingLoanEvent = objectMapper.readValue(consumerRecord.value(), HousingLoanEvent.class);
        log.info("housing loan:{}",housingLoanEvent);
        switch (housingLoanEvent.getLoanEventType()){
            case NEW:
                save(housingLoanEvent);
                break;
            case UPDATE:
                validate(housingLoanEvent);
                save(housingLoanEvent);
                break;
            default:
                log.info("not a valid housing loan event");
        }
    }

    private void validate(HousingLoanEvent housingLoanEvent) {
        try {
            Mono<Void> voidMono = housingLoanRepository.deleteById(housingLoanEvent.getHousingLoanId());
            voidMono.subscribe((k) -> System.out.println("old record deleted"), (Throwable e) -> System.out.println(e.getMessage()), () -> System.out.println("validation completed"));
            log.info("Validation done {}", housingLoanEvent);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void save(HousingLoanEvent housingLoanEvent) {
        Mono<HousingLoanEvent> save = housingLoanRepository.save(housingLoanEvent);
        save.subscribe((housingLoanEvent1)-> System.out.println(housingLoanEvent1),(Throwable e)-> System.out.println(e.getMessage()),()-> System.out.println("Completed the task"));
        log.info("successfully saved housing loan message to database:{}", housingLoanEvent);
    }
}
