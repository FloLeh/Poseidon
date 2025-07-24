package com.openclassrooms.poseidon.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.openclassrooms.poseidon.domain.User;
import com.openclassrooms.poseidon.exceptions.EntityNotFoundException;
import com.openclassrooms.poseidon.repositories.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setup() {
        user = new User(null, "johnDoe", "plainPassword", "John Doe", "USER");
    }

    @Test
    void create_shouldEncodePasswordAndSaveUser_whenValid() {
        // Arrange
        user.setId(null);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User u = invocation.getArgument(0);
            u.setId(1);
            return u;
        });

        // Act
        Integer id = userService.create(user);

        // Assert
        assertNotNull(id);
        assertEquals(1, id);
        // Password should be encoded (not equal to raw)
        assertNotEquals("plainPassword", user.getPassword());
        verify(userRepository).save(user);
    }

    @Test
    void create_shouldThrowException_whenIdNotNull() {
        user.setId(99);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> userService.create(user));
        assertTrue(ex.getMessage().contains("ID needs to be null"));
    }

    @Test
    void create_shouldThrowException_whenInvalidFields() {
        // Null user
        assertThrows(NullPointerException.class, () -> userService.create(null));

        // Empty username
        user.setUsername("");
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> userService.create(user));
        assertTrue(ex.getMessage().contains("Username must not be empty"));
        user.setUsername("johnDoe");

        // Empty password
        user.setPassword("");
        ex = assertThrows(IllegalArgumentException.class, () -> userService.create(user));
        assertTrue(ex.getMessage().contains("Password must not be empty"));
        user.setPassword("plainPassword");

        // Empty fullname
        user.setFullname("");
        ex = assertThrows(IllegalArgumentException.class, () -> userService.create(user));
        assertTrue(ex.getMessage().contains("FullName must not be empty"));
        user.setFullname("John Doe");

        // Empty role
        user.setRole("");
        ex = assertThrows(IllegalArgumentException.class, () -> userService.create(user));
        assertTrue(ex.getMessage().contains("Role must not be empty"));
    }

    @Test
    void update_shouldUpdateUserAndEncodePassword_whenValid() {
        // Arrange
        user.setId(1);
        User updatedUser = new User(null, "johnDoe", "newPassword", "John Doe Updated", "ADMIN");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        userService.update(updatedUser, 1);

        // Assert
        verify(userRepository).findById(1);
        verify(userRepository).save(user);
        assertEquals("johnDoe", user.getUsername());
        assertEquals("John Doe Updated", user.getFullname());
        assertEquals("ADMIN", user.getRole());
        assertNotEquals("newPassword", user.getPassword()); // Password encoded
    }

    @Test
    void update_shouldThrowEntityNotFoundException_whenUserNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.update(user, 1));
    }
}
