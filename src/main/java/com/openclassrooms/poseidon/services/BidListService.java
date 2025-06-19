package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.BidList;

import java.util.List;
import java.util.Optional;

public interface BidListService {
    List<BidList> getAllBidLists();
    BidList getBidListById(int id);
}
