package com.openclassrooms.poseidon.domain;

public interface DomainEntity<DOMAIN_ENTITY> {

    Integer getId();
    void update(DOMAIN_ENTITY entity);

}
