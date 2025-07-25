package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.DomainEntity;
import com.openclassrooms.poseidon.exceptions.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
public abstract class AbstractCrudService<DOMAIN_ENTITY extends DomainEntity<DOMAIN_ENTITY>> implements CrudInterface<DOMAIN_ENTITY> {
    protected final JpaRepository<DOMAIN_ENTITY, Integer> repository;

    protected AbstractCrudService(JpaRepository<DOMAIN_ENTITY, Integer> repository) {
        this.repository = repository;
    }

    @Override
    public DOMAIN_ENTITY getById(Integer id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<DOMAIN_ENTITY> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public void update(DOMAIN_ENTITY object, Integer id) {
        DOMAIN_ENTITY objectToUpdate = repository.findById(id).orElseThrow(EntityNotFoundException::new);

        objectToUpdate.update(object);

        repository.save(objectToUpdate);
    }

    @Override
    public Integer create(DOMAIN_ENTITY object) {
        Assert.notNull(object, "Object must not be null");
        Assert.isNull(object.getId(), "ID needs to be null");

        var savedObject = repository.save(object);
        return savedObject.getId();
    }
}
