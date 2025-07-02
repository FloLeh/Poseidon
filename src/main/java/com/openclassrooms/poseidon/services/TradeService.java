package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.Trade;
import com.openclassrooms.poseidon.exceptions.EntityNotFoundException;
import com.openclassrooms.poseidon.repositories.TradeRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class TradeService extends AbstractCrudService<Trade> {

    private final TradeRepository tradeRepository;

    public TradeService(TradeRepository repository) {
        super(repository);
        this.tradeRepository = repository;
    }

    @Override
    public Integer create(Trade trade) {
        Assert.isNull(trade.getId(), "ID needs to be null");
        validateFields(trade);

        Trade savedTrade = tradeRepository.save(trade);
        return savedTrade.getId();
    }

    @Override
    public void update(Trade trade, Integer id) {
        Trade tradeToUpdate = repository.findById(id).orElseThrow(EntityNotFoundException::new);

        validateFields(trade);

        tradeToUpdate.update(trade);

        tradeRepository.save(tradeToUpdate);
    }

    private void validateFields(Trade trade) {
        Assert.notNull(trade, "Object must not be null");
        Assert.hasLength(trade.getAccount(), "Account must not be empty");
        Assert.hasLength(trade.getType(), "Type must not be empty");
        Assert.isTrue(trade.getBuyQuantity() > 0, "BuyQuantity must be positive");
    }

}
