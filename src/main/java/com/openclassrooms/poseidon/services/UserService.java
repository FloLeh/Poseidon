package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.User;
import com.openclassrooms.poseidon.exceptions.EntityNotFoundException;
import com.openclassrooms.poseidon.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserService extends AbstractCrudService<User>{
    private final UserRepository userRepository;

    public UserService(UserRepository repository) {
        super(repository);
        this.userRepository = repository;
    }

    @Override
    public Integer create(User user) {
        Assert.isNull(user.getId(), "ID needs to be null");
        validateFields(user);
        encodePassword(user);

        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    @Override
    public void update(User user, Integer id) {
        User userToUpdate = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        validateFields(user);
        encodePassword(user);

        userToUpdate.update(user);

        userRepository.save(userToUpdate);
    }

    private void validateFields(User user) {
        Assert.notNull(user, "Object must not be null");
        Assert.hasLength(user.getUsername(), "Username must not be empty");
        Assert.hasLength(user.getPassword(), "Password must not be empty");
        Assert.hasLength(user.getFullname(), "FullName must not be empty");
        Assert.hasLength(user.getRole(), "Role must not be empty");
    }

    private void encodePassword(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
    }
}
