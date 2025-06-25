package com.openclassrooms.poseidon.repositories;

import com.openclassrooms.poseidon.domain.DomainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CrudRepository<DOMAIN_ENTITY extends DomainEntity<DOMAIN_ENTITY>> extends JpaRepository<DOMAIN_ENTITY, Integer> {
}