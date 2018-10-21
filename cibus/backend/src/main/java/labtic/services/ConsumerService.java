package labtic.services;

import entities.Consumer;
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

}
