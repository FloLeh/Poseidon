package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.User;
import com.openclassrooms.poseidon.repositories.UserRepository;

public class UserService extends AbstractCrudService<User>{
    public UserService(UserRepository repository) {
        super(repository);
    }
}
