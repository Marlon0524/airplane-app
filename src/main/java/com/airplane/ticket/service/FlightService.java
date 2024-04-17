package com.airplane.ticket.service;

import com.airplane.ticket.entity.Flight;
import com.airplane.ticket.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {

    @Autowired
    private final FlightRepository flightRepository;

    public Flight createFlight(Flight flight) {
        // Aquí podrías realizar validaciones adicionales antes de guardar el vuelo
        return flightRepository.save(flight);
    }

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }
    public Flight getFlightById(Integer id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vuelo no encontrado con ID: " + id));
    }

}