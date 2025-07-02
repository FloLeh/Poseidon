package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.RuleName;
import com.openclassrooms.poseidon.exceptions.EntityNotFoundException;
import com.openclassrooms.poseidon.repositories.RuleNameRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class RuleNameService extends AbstractCrudService<RuleName> {
    private final RuleNameRepository ruleNameRepository;

    public RuleNameService(RuleNameRepository repository) {
        super(repository);
        this.ruleNameRepository = repository;
    }

    @Override
    public Integer create(RuleName ruleName) {
        Assert.isNull(ruleName.getId(), "ID needs to be null");
        validateFields(ruleName);

        RuleName savedRuleName = ruleNameRepository.save(ruleName);
        return savedRuleName.getId();
    }

    @Override
    public void update(RuleName ruleName, Integer id) {
        RuleName ruleNameToUpdate = repository.findById(id).orElseThrow(EntityNotFoundException::new);

        validateFields(ruleName);

        ruleNameToUpdate.update(ruleName);

        ruleNameRepository.save(ruleNameToUpdate);
    }

    private void validateFields(RuleName ruleName) {
        Assert.notNull(ruleName, "Object must not be null");
        Assert.hasLength(ruleName.getName(), "Name must not be empty");
        Assert.hasLength(ruleName.getDescription(), "Description must not be empty");
        Assert.hasLength(ruleName.getJson(), "Json must not be empty");
        Assert.hasLength(ruleName.getTemplate(), "Template must not be empty");
        Assert.hasLength(ruleName.getSql(), "SQL must not be empty");
        Assert.hasLength(ruleName.getSqlPart(), "SQLPart must not be empty");
    }
}
