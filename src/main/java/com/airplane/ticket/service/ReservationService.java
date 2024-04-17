package com.airplane.ticket.service;

import com.airplane.ticket.entity.ApiResponse;
import com.airplane.ticket.entity.Flight;
import com.airplane.ticket.entity.Reservation;

import com.airplane.ticket.repository.FlightRepository;
import com.airplane.ticket.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {
    @Autowired
    private final ReservationRepository reservationRepository;
    private final FlightRepository flightRepository;
    public ResponseEntity<ApiResponse<Reservation>> createReservation(Reservation reservation) {
        try {
            // Obtener el ID del vuelo desde la reserva
            Integer flightId = reservation.getFlight().getFlight_id();

            // Verificar si el vuelo existe en la base de datos
            Optional<Flight> existingFlightOptional = flightRepository.findById(flightId);
            if (existingFlightOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse<>(null, "error", "Vuelo no encontrado con ID: " + flightId));
            }

            // Obtener la instancia existente de vuelo desde la base de datos
            Flight existingFlight = existingFlightOptional.get();

            // Asignar la instancia existente de vuelo a la reserva
            reservation.setFlight(existingFlight);

            // Guardar la reserva en la base de datos
            Reservation newReservation = reservationRepository.save(reservation);

            // Construir el objeto ApiResponse con los datos de la reserva creada
            ApiResponse<Reservation> response = new ApiResponse<>(newReservation, "success", "Reserva creada");

            // Devolver el ResponseEntity con el ApiResponse
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            // Manejar cualquier excepción que pueda ocurrir durante el proceso
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(null, "error", "Error al crear la reserva: " + e.getMessage()));
        }
    }

    public ApiResponse<Reservation> updateReservation(Integer id, Reservation updatedReservation) {
        Reservation existingReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));

        // Actualizar los campos de la reserva existente con los datos proporcionados en updatedReservation
        existingReservation.setReservationNumber(updatedReservation.getReservationNumber());
        existingReservation.setDateFormatted(updatedReservation.getDateFormatted());
        existingReservation.setDate(updatedReservation.getDate());
        existingReservation.setTicketId(updatedReservation.getTicketId());
        existingReservation.setFlight(updatedReservation.getFlight());

        // Guardar la reserva actualizada en la base de datos
        Reservation updatedEntity = reservationRepository.save(existingReservation);

        // Devolver la reserva actualizada
        return new ApiResponse<>(updatedEntity, "success", "Reserva modificada satisfactoriamente");
    }



    public Reservation getReservationById(Integer id) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        return optionalReservation.orElse(null);
    }

    public List<Reservation> getAllReservations(){
        return reservationRepository.findAll();
    }

}
