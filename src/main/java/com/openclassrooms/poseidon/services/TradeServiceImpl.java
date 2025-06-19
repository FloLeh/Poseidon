package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.Trade;
import com.openclassrooms.poseidon.repositories.CrudRepository;
import com.openclassrooms.poseidon.repositories.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeServiceImpl extends AbstractCrudService<Trade> {

    public TradeServiceImpl(CrudRepository<Trade> repository) {
        super(repository);
    }

    @Override
    public void save(Trade trade){
        /*
        traitement XYZ
         */
        repository.save(trade);
    }

}
