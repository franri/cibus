package labtic.services;


import entities.Neighbourhood;
import labtic.database.NeighbourhoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NeighbourhoodService {

    @Autowired
    NeighbourhoodRepository neighbourhoodRepository;

    public List<Neighbourhood> findAllNeighbourhoods(){
        return neighbourhoodRepository.findAll();
    }

    public void save(Neighbourhood neighbourhood){neighbourhoodRepository.save(neighbourhood);}
}
