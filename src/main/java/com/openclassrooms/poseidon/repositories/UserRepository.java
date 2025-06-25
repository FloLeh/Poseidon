package com.openclassrooms.poseidon.repositories;

import com.openclassrooms.poseidon.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User> {
    Optional<User> findByUsername(String username);
}
