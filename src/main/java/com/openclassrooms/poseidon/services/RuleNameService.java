package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.RuleName;
import com.openclassrooms.poseidon.repositories.RuleNameRepository;
import org.springframework.stereotype.Service;

@Service
public class RuleNameService extends AbstractCrudService<RuleName> {
    public RuleNameService(RuleNameRepository repository) {
        super(repository);
    }
}
