package com.openclassrooms.poseidon.services;


import com.openclassrooms.poseidon.domain.BidList;
import com.openclassrooms.poseidon.repositories.BidListRepository;
import org.springframework.stereotype.Service;

@Service
public class BidListService extends AbstractCrudService<BidList> {
    public BidListService(BidListRepository repository) {
        super(repository);
    }

}
