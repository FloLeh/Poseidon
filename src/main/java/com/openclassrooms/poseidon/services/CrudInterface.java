package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.DomainEntity;

import java.util.List;

public interface CrudInterface<DOMAIN_ENTITY extends DomainEntity<DOMAIN_ENTITY>> {

    DOMAIN_ENTITY getById(Integer id);
    List<DOMAIN_ENTITY> findAll();
    void deleteById(Integer id);
    void update(DOMAIN_ENTITY object, Integer id);
    Integer create(DOMAIN_ENTITY object);

}
