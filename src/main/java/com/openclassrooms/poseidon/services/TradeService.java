package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.Trade;
import com.openclassrooms.poseidon.repositories.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class TradeService extends AbstractCrudService<Trade> {

    public TradeService(CrudRepository<Trade> repository) {
        super(repository);
    }

}
