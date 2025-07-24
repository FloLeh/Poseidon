package com.openclassrooms.poseidon.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.openclassrooms.poseidon.domain.Trade;
import com.openclassrooms.poseidon.exceptions.EntityNotFoundException;
import com.openclassrooms.poseidon.repositories.TradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TradeServiceTest {

    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private TradeService tradeService;

    private Trade trade;

    @BeforeEach
    void setup() {
        trade = new Trade("account1", "type1", 100.0);
    }

    @Test
    void create_shouldSaveAndReturnId_whenValidTrade() {
        // Arrange
        trade.setTradeId(null); // ID null as required by create()
        Trade savedTrade = new Trade("account1", "type1", 100.0);
        savedTrade.setTradeId(1);
        when(tradeRepository.save(trade)).thenReturn(savedTrade);

        // Act
        Integer resultId = tradeService.create(trade);

        // Assert
        assertEquals(1, resultId);
        verify(tradeRepository).save(trade);
    }

    @Test
    void create_shouldThrowException_whenIdNotNull() {
        trade.setTradeId(99);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> tradeService.create(trade));
        assertTrue(thrown.getMessage().contains("ID needs to be null"));
    }

    @Test
    void update_shouldSaveUpdatedTrade_whenValid() {
        // Arrange
        trade.setTradeId(1);
        Trade updatedTrade = new Trade("account2", "type2", 200.0);
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));
        when(tradeRepository.save(any())).thenReturn(trade);

        // Act
        tradeService.update(updatedTrade, 1);

        // Assert
        verify(tradeRepository).findById(1);
        verify(tradeRepository).save(trade);
        assertEquals("account2", trade.getAccount());
        assertEquals("type2", trade.getType());
        assertEquals(200.0, trade.getBuyQuantity());
    }

    @Test
    void update_shouldThrowEntityNotFoundException_whenTradeNotFound() {
        when(tradeRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> tradeService.update(trade, 1));
    }

    @Test
    void validateFields_shouldThrowException_whenInvalid() {
        // Null object
        assertThrows(NullPointerException.class, () -> tradeService.create(null));

        // Account empty
        trade.setAccount("");
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> tradeService.create(trade));
        assertTrue(ex.getMessage().contains("Account must not be empty"));
        trade.setAccount("account1");

        // Type empty
        trade.setType("");
        ex = assertThrows(IllegalArgumentException.class, () -> tradeService.create(trade));
        assertTrue(ex.getMessage().contains("Type must not be empty"));
        trade.setType("type1");

        // BuyQuantity zero or negative
        trade.setBuyQuantity(0.0);
        ex = assertThrows(IllegalArgumentException.class, () -> tradeService.create(trade));
        assertTrue(ex.getMessage().contains("BuyQuantity must be positive"));
        trade.setBuyQuantity(-10.0);
        ex = assertThrows(IllegalArgumentException.class, () -> tradeService.create(trade));
        assertTrue(ex.getMessage().contains("BuyQuantity must be positive"));
    }
}
