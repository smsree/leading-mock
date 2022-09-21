package com.example.loanserviceconsumer.consumerService;

import com.example.loanserviceconsumer.domain.VehicleLoanEvent;
import com.example.loanserviceconsumer.repository.VehicleLoanRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class VehicleLoanConsumerService {
    @Autowired
    VehicleLoanRepository vehicleLoanRepository;
    ObjectMapper objectMapper = new ObjectMapper();

    public void processVehicleLoanEventsPostAndPut(ConsumerRecord<String, String> consumerRecord) throws JsonProcessingException {
        VehicleLoanEvent vehicleLoanEvent = objectMapper.readValue(consumerRecord.value(), VehicleLoanEvent.class);
        log.info("vehicle loan : {}", vehicleLoanEvent);
        switch (vehicleLoanEvent.getLoanEventType()){
            case NEW:
                save(vehicleLoanEvent);
                break;
            case UPDATE:
                validate(vehicleLoanEvent);
                save(vehicleLoanEvent);
                break;
            default:
                log.info("invalid vehicle loan event type");
        }
    }

    private void validate(VehicleLoanEvent vehicleLoanEvent) {
        try {
            Mono<Void> voidMono = vehicleLoanRepository.deleteById(vehicleLoanEvent.getVehicleLoanId());
            voidMono.subscribe((k) -> System.out.println("old record deleted"), (Throwable e) -> System.out.println(e.getMessage()), () -> System.out.println("validation completed"));
            log.info("Validation done {}", vehicleLoanEvent);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        log.info("Validation done for vehicle loan {}",vehicleLoanEvent);
    }

    private void save(VehicleLoanEvent vehicleLoanEvent) {
        Mono<VehicleLoanEvent> save = vehicleLoanRepository.save(vehicleLoanEvent);
        save.subscribe((vehicleLoanEvent1)-> System.out.println(vehicleLoanEvent1),(Throwable e)-> System.out.println(e.getMessage()),()-> System.out.println("Completed the task"));
        log.info("successfully saved vehicle loan message to database:{}", vehicleLoanEvent);
    }
}
