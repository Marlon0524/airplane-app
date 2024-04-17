package com.airplane.ticket.controller;

import com.airplane.ticket.entity.ApiResponse;
import com.airplane.ticket.entity.Reservation;
import com.airplane.ticket.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationControllerTest {
    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationController reservationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllReservations_NoReservations_ReturnsNotFoundResponse() {
    }



    @Test
    void getAllReservations() {
    }

    @Test
    void updateReservation() {
    }
}