package labtic.services;

import entities.Consumer;
import exceptions.NoConsumerFound;
import labtic.database.ConsumerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {
    @Autowired
    ConsumerRepository consumerRepository;

    public void save(Consumer consumer){
        consumerRepository.save(consumer);
    }

    public Consumer findByEmail(String email) throws NoConsumerFound {
        Consumer consumer = consumerRepository.findOneByEmail(email);
        if(consumer == null){
            throw new NoConsumerFound(null);
        }
        return consumer;
    }

}
