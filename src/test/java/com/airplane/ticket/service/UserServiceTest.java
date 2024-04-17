package com.airplane.ticket.service;

import com.airplane.ticket.entity.User;
import com.airplane.ticket.repository.UserRespository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRespository userRespository;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser() {
        // Arrange
        User user = new User();
        when(userRespository.save(user)).thenReturn(user);

        // Act
        User createdUser = userService.createUser(user);

        // Assert
        assertEquals(user, createdUser);
    }

    @Test
    void getAllUsers() {
        // Arrange
        List<User> users = new ArrayList<>();
        when(userRespository.findAll()).thenReturn(users);

        // Act
        List<User> retrievedUsers = userService.getAllUsers();

        // Assert
        assertEquals(users, retrievedUsers);
    }

    @Test
    void updateUser() {
        Integer id = 1;
        User existingUser = new User();
        existingUser.setId(id);
        User updatedUser = new User();
        updatedUser.setFirstName("John");
        updatedUser.setLastName("Doe");
        updatedUser.setDocumentNumber(Integer.valueOf("123456789"));
        when(userRespository.findById(id)).thenReturn(Optional.of(existingUser));
        when(userRespository.save(existingUser)).thenReturn(existingUser);

        // Act
        User modifiedUser = userService.updateUser(id, updatedUser);

        // Assert
        assertEquals(updatedUser.getFirstName(), modifiedUser.getFirstName());
        assertEquals(updatedUser.getLastName(), modifiedUser.getLastName());
        assertEquals(updatedUser.getDocumentNumber(), modifiedUser.getDocumentNumber());
    }
}