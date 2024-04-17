package com.airplane.ticket.service;

import com.airplane.ticket.entity.Flight;
import com.airplane.ticket.repository.FlightRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FlightServiceTest {

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private FlightService flightService;

    @Test
    void createFlight() {
        // Arrange
        Flight flight = new Flight();
        when(flightRepository.save(flight)).thenReturn(flight);

        // Act
        Flight createdFlight = flightService.createFlight(flight);

        // Assert
        assertEquals(flight, createdFlight);
    }

    @Test
    void getAllFlights() {
        // Arrange
        Integer id = 1;
        Flight flight = new Flight();
        flight.setFlight_id(id);
        when(flightRepository.findById(id)).thenReturn(Optional.of(flight));

        // Act
        Flight retrievedFlight = flightService.getFlightById(id);

        // Assert
        assertEquals(flight, retrievedFlight);
    }

    @Test
    void getFlightById() {
        // Arrange
        Integer id = 1;
        when(flightRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> flightService.getFlightById(id));
    }
    }
