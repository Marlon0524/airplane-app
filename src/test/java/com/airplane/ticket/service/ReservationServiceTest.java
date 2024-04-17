package com.airplane.ticket.service;

import com.airplane.ticket.entity.ApiResponse;
import com.airplane.ticket.entity.Flight;
import com.airplane.ticket.entity.Reservation;
import com.airplane.ticket.repository.FlightRepository;
import com.airplane.ticket.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private ReservationService reservationService;
    @Test
    void createReservation() {
        Reservation reservation = new Reservation();
        reservation.setReservationNumber(Integer.valueOf("12345"));

        Flight flight = new Flight();
        flight.setFlight_id(1);
        reservation.setFlight(flight);

        when(flightRepository.findById(1)).thenReturn(Optional.of(flight));
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        // Act
        ResponseEntity<ApiResponse<Reservation>> responseEntity = reservationService.createReservation(reservation);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        ApiResponse<Reservation> apiResponse = responseEntity.getBody();
        assertEquals("success", apiResponse.getStatus());
        assertEquals("Reserva creada", apiResponse.getMessage());
        assertEquals(reservation, apiResponse.getData());
    }

    @Test
    void updateReservation() {
        // Arrange
        Reservation reservation = new Reservation();
        Flight flight = new Flight();
        flight.setFlight_id(1);
        reservation.setFlight(flight);

        when(flightRepository.findById(1)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<ApiResponse<Reservation>> responseEntity = reservationService.createReservation(reservation);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        ApiResponse<Reservation> apiResponse = responseEntity.getBody();
        assertEquals("error", apiResponse.getStatus());
        assertEquals("Vuelo no encontrado con ID: 1", apiResponse.getMessage());
        assertEquals(null, apiResponse.getData());
    }

    @Test
    void getReservationById() {
        // Arrange
        Reservation reservation = new Reservation();
        reservation.setId(1);
        when(reservationRepository.findById(1)).thenReturn(Optional.of(reservation));

        // Act
        Reservation result = reservationService.getReservationById(1);

        // Assert
        assertEquals(reservation, result);
    }

    @Test
    void getAllReservations() {
        // Arrange
        Reservation reservation1 = new Reservation();
        reservation1.setId(1);
        Reservation reservation2 = new Reservation();
        reservation2.setId(2);
        List<Reservation> reservations = Arrays.asList(reservation1, reservation2);
        when(reservationRepository.findAll()).thenReturn(reservations);

        // Act
        List<Reservation> result = reservationService.getAllReservations();

        // Assert
        assertEquals(reservations.size(), result.size());
        assertEquals(reservations, result);
    }
}