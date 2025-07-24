package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.CurvePoint;
import com.openclassrooms.poseidon.exceptions.EntityNotFoundException;
import com.openclassrooms.poseidon.repositories.CurvePointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurvePointServiceTest {

    @Mock
    private CurvePointRepository curvePointRepository;

    private CurvePointService curvePointService;

    @BeforeEach
    void setUp() {
        curvePointService = new CurvePointService(curvePointRepository);
    }

    @Test
    void create_shouldSaveValidCurvePointAndReturnId() {
        // Given
        CurvePoint curvePoint = new CurvePoint(10, 5.0, 100.0);
        CurvePoint savedCurvePoint = new CurvePoint(10, 5.0, 100.0);
        savedCurvePoint.setId(1);

        when(curvePointRepository.save(curvePoint)).thenReturn(savedCurvePoint);

        // When
        Integer result = curvePointService.create(curvePoint);

        // Then
        assertEquals(1, result);
        verify(curvePointRepository, times(1)).save(curvePoint);
    }

    @Test
    void create_shouldThrowExceptionIfInvalidValue() {
        // Given: invalid term (negative)
        CurvePoint invalidCurvePoint = new CurvePoint(10, -2.0, 50.0);

        // When + Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                curvePointService.create(invalidCurvePoint)
        );
        assertTrue(exception.getMessage().contains("Term must be positive"));
        verify(curvePointRepository, never()).save(any());
    }

    @Test
    void update_shouldUpdateExistingCurvePoint() {
        // Given
        int id = 1;
        CurvePoint existing = new CurvePoint(1, 1.0, 1.0);
        existing.setId(id);

        CurvePoint updateData = new CurvePoint(2, 2.0, 2.0);

        when(curvePointRepository.findById(id)).thenReturn(Optional.of(existing));
        when(curvePointRepository.save(existing)).thenReturn(existing);

        // When
        curvePointService.update(updateData, id);

        // Then
        verify(curvePointRepository).findById(id);
        verify(curvePointRepository).save(existing);

        assertEquals(2, existing.getCurveId());
        assertEquals(2.0, existing.getTerm());
        assertEquals(2.0, existing.getValue());
    }

    @Test
    void update_shouldThrowIfCurvePointNotFound() {
        // Given
        int id = 999;
        CurvePoint updateData = new CurvePoint(1, 1.0, 1.0);

        when(curvePointRepository.findById(id)).thenReturn(Optional.empty());

        // When + Then
        assertThrows(EntityNotFoundException.class, () ->
                curvePointService.update(updateData, id)
        );
        verify(curvePointRepository, never()).save(any());
    }
}
