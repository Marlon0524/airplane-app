package com.airplane.ticket.controller;

import com.airplane.ticket.entity.ApiResponse;
import com.airplane.ticket.entity.Flight;
import com.airplane.ticket.repository.FlightRepository;
import com.airplane.ticket.service.FlightService;
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
class FlightControllerTest {

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private FlightService flightService;

    @InjectMocks
    private FlightController flightController;
    @Test
    void createFlight() {
        // Arrange
        Flight flight = new Flight();
        when(flightService.createFlight(flight)).thenReturn(flight);

        // Act
        ResponseEntity<ApiResponse<Flight>> responseEntity = flightController.createFlight(flight);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("success", responseEntity.getBody().getStatus());
        assertEquals("Vuelo creado satisfactoriamente", responseEntity.getBody().getMessage());
        assertEquals(flight, responseEntity.getBody().getData());
    }

    @Test
    void getAllFlights() {
        // Arrange
        List<Flight> flights = new ArrayList<>();
        when(flightRepository.findAll()).thenReturn(flights);

        // Act
        ResponseEntity<List<Flight>> responseEntity = flightController.getAllFlights();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(flights, responseEntity.getBody());
    }
}