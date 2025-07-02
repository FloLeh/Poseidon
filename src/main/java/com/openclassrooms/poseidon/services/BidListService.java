package com.openclassrooms.poseidon.services;


import com.openclassrooms.poseidon.domain.BidList;
import com.openclassrooms.poseidon.exceptions.EntityNotFoundException;
import com.openclassrooms.poseidon.repositories.BidListRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class BidListService extends AbstractCrudService<BidList> {
    private final BidListRepository bidListRepository;

    public BidListService(BidListRepository repository, BidListRepository bidListRepository) {
        super(repository);
        this.bidListRepository = bidListRepository;
    }

    @Override
    public Integer create(BidList bidList) {
        Assert.isNull(bidList.getId(), "ID needs to be null");
        validateFields(bidList);

        BidList savedBidList = bidListRepository.save(bidList);
        return savedBidList.getId();
    }

    @Override
    public void update(BidList bidList, Integer id) {
        BidList bidListToUpdate = repository.findById(id).orElseThrow(EntityNotFoundException::new);

        validateFields(bidList);

        bidListToUpdate.update(bidList);

        bidListRepository.save(bidListToUpdate);
    }

    private void validateFields(BidList bidList) {
        Assert.notNull(bidList, "Object must not be null");
        Assert.hasLength(bidList.getAccount(), "Account must not be empty");
        Assert.hasLength(bidList.getType(), "Type must not be empty");
        Assert.notNull(bidList.getBidQuantity(), "BidQuantity must not be null");
        Assert.isTrue(bidList.getBidQuantity() > 0, "BidQuantity must be positive");
    }

}
