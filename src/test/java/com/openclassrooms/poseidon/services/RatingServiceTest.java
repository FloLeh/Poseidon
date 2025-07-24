package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.Rating;
import com.openclassrooms.poseidon.exceptions.EntityNotFoundException;
import com.openclassrooms.poseidon.repositories.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    private RatingService ratingService;

    @BeforeEach
    void setUp() {
        ratingService = new RatingService(ratingRepository);
    }

    @Test
    void create_shouldSaveValidRatingAndReturnId() {
        // Given
        Rating rating = new Rating("Aaa", "AAA", "AAA", 1);
        Rating savedRating = new Rating("Aaa", "AAA", "AAA", 1);
        savedRating.setId(42);

        when(ratingRepository.save(rating)).thenReturn(savedRating);

        // When
        Integer result = ratingService.create(rating);

        // Then
        assertEquals(42, result);
        verify(ratingRepository, times(1)).save(rating);
    }

    @Test
    void create_shouldThrowExceptionForInvalidOrder() {
        // Given: order is zero (invalid)
        Rating rating = new Rating("Aaa", "AAA", "AAA", 0);

        // When + Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                ratingService.create(rating)
        );
        assertTrue(exception.getMessage().contains("Order must be positive"));
        verify(ratingRepository, never()).save(any());
    }

    @Test
    void update_shouldUpdateExistingRating() {
        // Given
        int id = 5;
        Rating existing = new Rating("Baa", "BB", "BBB", 2);
        existing.setId(id);

        Rating updatedData = new Rating("Aaa", "AAA", "AAA", 1);

        when(ratingRepository.findById(id)).thenReturn(Optional.of(existing));
        when(ratingRepository.save(existing)).thenReturn(existing);

        // When
        ratingService.update(updatedData, id);

        // Then
        verify(ratingRepository).findById(id);
        verify(ratingRepository).save(existing);

        assertEquals("Aaa", existing.getMoodysRating());
        assertEquals("AAA", existing.getSandPRating());
        assertEquals("AAA", existing.getFitchRating());
        assertEquals(1, existing.getOrder());
    }

    @Test
    void update_shouldThrowIfRatingNotFound() {
        // Given
        int id = 999;
        Rating updateData = new Rating("Aaa", "AAA", "AAA", 1);

        when(ratingRepository.findById(id)).thenReturn(Optional.empty());

        // When + Then
        assertThrows(EntityNotFoundException.class, () ->
                ratingService.update(updateData, id)
        );
        verify(ratingRepository, never()).save(any());
    }
}
