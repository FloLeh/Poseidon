package com.openclassrooms.poseidon.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.openclassrooms.poseidon.domain.RuleName;
import com.openclassrooms.poseidon.exceptions.EntityNotFoundException;
import com.openclassrooms.poseidon.repositories.RuleNameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class RuleNameServiceTest {

    @Mock
    private RuleNameRepository ruleNameRepository;

    @InjectMocks
    private RuleNameService ruleNameService;

    private RuleName ruleName;

    @BeforeEach
    void setUp() {
        ruleName = new RuleName(
                "Name",
                "Description",
                "{\"key\":\"value\"}",
                "Template content",
                "SELECT * FROM table",
                "sqlPartContent"
        );
    }

    @Test
    void create_shouldSaveAndReturnId_whenValidRuleName() {
        // given
        ruleName.setId(null);
        RuleName savedRuleName = new RuleName(
                "Name",
                "Description",
                "{\"key\":\"value\"}",
                "Template content",
                "SELECT * FROM table",
                "sqlPartContent"
        );
        savedRuleName.setId(1);
        when(ruleNameRepository.save(ruleName)).thenReturn(savedRuleName);

        // when
        Integer id = ruleNameService.create(ruleName);

        // then
        assertEquals(1, id);
        verify(ruleNameRepository, times(1)).save(ruleName);
    }

    @Test
    void create_shouldThrowException_whenIdIsNotNull() {
        // given
        ruleName.setId(5);

        // when / then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ruleNameService.create(ruleName);
        });
        assertTrue(exception.getMessage().contains("ID needs to be null"));
    }

    @Test
    void update_shouldUpdateExistingRuleName_whenValid() {
        // given
        int idToUpdate = 1;
        ruleName.setId(null);

        RuleName existingRuleName = new RuleName(
                "OldName",
                "OldDescription",
                "{}",
                "OldTemplate",
                "SELECT * FROM old_table",
                "oldSqlPart"
        );
        existingRuleName.setId(idToUpdate);

        when(ruleNameRepository.findById(idToUpdate)).thenReturn(Optional.of(existingRuleName));
        when(ruleNameRepository.save(any(RuleName.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        ruleNameService.update(ruleName, idToUpdate);

        // then
        verify(ruleNameRepository).findById(idToUpdate);
        verify(ruleNameRepository).save(existingRuleName);
        assertEquals(ruleName.getName(), existingRuleName.getName());
        assertEquals(ruleName.getDescription(), existingRuleName.getDescription());
        assertEquals(ruleName.getJson(), existingRuleName.getJson());
        assertEquals(ruleName.getTemplate(), existingRuleName.getTemplate());
        assertEquals(ruleName.getSql(), existingRuleName.getSql());
        assertEquals(ruleName.getSqlPart(), existingRuleName.getSqlPart());
    }

    @Test
    void update_shouldThrowEntityNotFoundException_whenRuleNameNotFound() {
        // given
        int nonExistingId = 99;
        when(ruleNameRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // when / then
        assertThrows(EntityNotFoundException.class, () -> ruleNameService.update(ruleName, nonExistingId));
    }

    @Test
    void validateFields_shouldThrowException_whenInvalidFields() {
        // Not null check
        assertThrows(NullPointerException.class, () -> ruleNameService.create(null));

        // Empty name
        ruleName.setName("");
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> ruleNameService.create(ruleName));
        assertTrue(ex.getMessage().contains("Name must not be empty"));
        ruleName.setName("Name"); // reset

        // Empty description
        ruleName.setDescription("");
        ex = assertThrows(IllegalArgumentException.class, () -> ruleNameService.create(ruleName));
        assertTrue(ex.getMessage().contains("Description must not be empty"));
        ruleName.setDescription("Description"); // reset

        // Empty json
        ruleName.setJson("");
        ex = assertThrows(IllegalArgumentException.class, () -> ruleNameService.create(ruleName));
        assertTrue(ex.getMessage().contains("Json must not be empty"));
        ruleName.setJson("{\"key\":\"value\"}"); // reset

        // Empty template
        ruleName.setTemplate("");
        ex = assertThrows(IllegalArgumentException.class, () -> ruleNameService.create(ruleName));
        assertTrue(ex.getMessage().contains("Template must not be empty"));
        ruleName.setTemplate("Template content"); // reset

        // Empty sql
        ruleName.setSql("");
        ex = assertThrows(IllegalArgumentException.class, () -> ruleNameService.create(ruleName));
        assertTrue(ex.getMessage().contains("SQL must not be empty"));
        ruleName.setSql("SELECT * FROM table"); // reset

        // Empty sqlPart
        ruleName.setSqlPart("");
        ex = assertThrows(IllegalArgumentException.class, () -> ruleNameService.create(ruleName));
        assertTrue(ex.getMessage().contains("SQLPart must not be empty"));
        ruleName.setSqlPart("sqlPartContent"); // reset
    }

}
