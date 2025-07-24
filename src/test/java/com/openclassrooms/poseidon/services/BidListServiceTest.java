package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.BidList;
import com.openclassrooms.poseidon.exceptions.EntityNotFoundException;
import com.openclassrooms.poseidon.repositories.BidListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BidListServiceTest {

    @Mock
    private BidListRepository bidListRepository;

    private BidListService bidListService;

    @BeforeEach
    void setUp() {
        bidListService = new BidListService(bidListRepository, bidListRepository);
    }

    @Test
    void create_shouldSaveValidBidListAndReturnId() {
        // Given
        BidList bidList = new BidList("account1", "type1", 10.0);
        BidList savedBidList = new BidList("account1", "type1", 10.0);
        savedBidList.setBidListId(1);

        when(bidListRepository.save(bidList)).thenReturn(savedBidList);

        // When
        Integer resultId = bidListService.create(bidList);

        // Then
        assertEquals(1, resultId);
        verify(bidListRepository, times(1)).save(bidList);
    }

    @Test
    void update_shouldUpdateExistingBidList() {
        // Given
        int id = 1;
        BidList existing = new BidList("account1", "type1", 5.0);
        existing.setBidListId(id);

        BidList updated = new BidList("account1", "type1", 15.0);

        when(bidListRepository.findById(id)).thenReturn(Optional.of(existing));
        when(bidListRepository.save(any(BidList.class))).thenReturn(existing);

        // When
        bidListService.update(updated, id);

        // Then
        verify(bidListRepository, times(1)).findById(id);
        verify(bidListRepository, times(1)).save(existing);
        assertEquals(15.0, existing.getBidQuantity());
    }

    @Test
    void update_shouldThrowWhenBidListNotFound() {
        // Given
        int id = 99;
        BidList input = new BidList("accountX", "typeX", 20.0);

        when(bidListRepository.findById(id)).thenReturn(Optional.empty());

        // When + Then
        assertThrows(EntityNotFoundException.class, () -> bidListService.update(input, id));
        verify(bidListRepository, never()).save(any());
    }

    @Test
    void create_shouldThrowWhenBidQuantityNegative() {
        // Given
        BidList bidList = new BidList("account", "type", -5.0);

        // Then
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> bidListService.create(bidList));
        assertTrue(ex.getMessage().contains("positive"));
        verify(bidListRepository, never()).save(any());
    }
}
