package com.airplane.ticket.controller;

import com.airplane.ticket.entity.ApiResponse;
import com.airplane.ticket.entity.User;
import com.airplane.ticket.service.UserService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;
    @Test
    void create() {
        // Arrange
        User user = new User();
        when(userService.createUser(user)).thenReturn(user);

        // Act
        ResponseEntity<ApiResponse<User>> response = userController.create(user);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody().getData());
    }


    @Test
    void getAllUsers() {
        List<User> users = new ArrayList<>();
        when(userService.getAllUsers()).thenReturn(users);

        // Act
        ResponseEntity<ApiResponse<List<User>>> response = userController.getAllUsers();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(users, response.getBody().getData());
    }

    @Test
    void updateUser() {
        // Arrange
        Integer id = 1;
        User updatedUser = new User();
        when(userService.updateUser(id, updatedUser)).thenReturn(updatedUser);

        // Act
        ResponseEntity<ApiResponse<User>> response = userController.updateUser(updatedUser, id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedUser, response.getBody().getData());
    }
}