package com.openclassrooms.poseidon.repositories;

import com.openclassrooms.poseidon.domain.DomainEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrudRepository<DOMAIN_ENTITY extends DomainEntity> extends JpaRepository<DOMAIN_ENTITY, Integer> {
}
