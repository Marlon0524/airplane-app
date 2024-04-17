package com.airplane.ticket.controller;

import com.airplane.ticket.entity.ApiResponse;
import com.airplane.ticket.entity.Flight;
import com.airplane.ticket.repository.FlightRepository;
import com.airplane.ticket.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/flight")
@RequiredArgsConstructor
public class FlightController {

    private final FlightRepository flightRepository;
    private final FlightService flightService;
    @PostMapping
    public ResponseEntity<ApiResponse<Flight>> createFlight(@RequestBody Flight flight) {
        Flight createdFlight = flightService.createFlight(flight);
        ApiResponse<Flight> response = new ApiResponse<>(createdFlight, "success", "Vuelo creado satisfactoriamente");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping
    public ResponseEntity<List<Flight>> getAllFlights() {
        List<Flight> flights = flightRepository.findAll();
        return ResponseEntity.ok(flights);
    }
}
