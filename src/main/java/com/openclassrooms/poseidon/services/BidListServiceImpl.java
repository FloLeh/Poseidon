package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.BidList;
import com.openclassrooms.poseidon.repositories.BidListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BidListServiceImpl implements BidListService {

    private final BidListRepository bidListRepository;

    @Override
    public List<BidList> getAllBidLists() {
        return bidListRepository.findAll();
    }

    @Override
    public BidList getBidListById(int id) {
        return bidListRepository.findById(id).orElseThrow();
    }

}
