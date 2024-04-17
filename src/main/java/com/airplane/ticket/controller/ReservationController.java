package com.airplane.ticket.controller;

import com.airplane.ticket.entity.ApiResponse;
import com.airplane.ticket.entity.Flight;
import com.airplane.ticket.entity.Reservation;
import com.airplane.ticket.repository.FlightRepository;
import com.airplane.ticket.repository.ReservationRepository;
import com.airplane.ticket.service.FlightService;
import com.airplane.ticket.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {
    @Autowired
    private final ReservationService reservationService;
    private final ReservationRepository reservationRepository;
    private final FlightRepository flightRepository;
    private final FlightService flightService;
    private static final Logger logger = Logger.getLogger(ReservationController.class.getName());
    @PostMapping
    public ResponseEntity<ApiResponse<Reservation>> createReservation(@RequestBody Reservation reservation) {
        try {
            // Obtener el ID del vuelo desde la reserva
            Integer flightId = reservation.getFlightId();

            // Verificar si el vuelo existe en la base de datos
            Flight flight = flightService.getFlightById(flightId);
            if (flight == null) {
                // Devolver un error al cliente si el vuelo no se encuentra
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse<>(null, "error", "Vuelo no encontrado con ID: " + flightId));
            }

            // Asignar el vuelo a la reserva
            reservation.setFlight(flight);

            // Crear la reserva
            ResponseEntity<ApiResponse<Reservation>> reservationResponse = reservationService.createReservation(reservation);

            // Extraer la reserva del cuerpo del ResponseEntity
            Reservation newReservation = reservationResponse.getBody().getData();

            // Verificar si la respuesta contiene la reserva
            if (newReservation != null) {
                // La reserva se creó correctamente, construir el objeto ApiResponse con los datos de la reserva creada
                ApiResponse<Reservation> response = new ApiResponse<>(newReservation, "success", "Reserva creada");
                // Devolver el ResponseEntity con el ApiResponse
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                // Si la respuesta no contiene la reserva, devolver un error al cliente
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse<>(null, "error", "Error al crear la reserva"));
            }
        } catch (IllegalArgumentException e) {
            // Capturar la excepción cuando el vuelo no se encuentra
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(null, "error", e.getMessage()));
        } catch (Exception e) {
            // Manejar cualquier otra excepción que ocurra durante la creación de la reserva
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(null, "error", "Error al procesar la solicitud"));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Reservation>>> getAllReservations() {
        logger.info("Llamada al endpoint GET /restaurant recibida.");
        List<Reservation> reservations = reservationService.getAllReservations();
        ApiResponse<List<Reservation>> response;
        if (!reservations.isEmpty()) {
            response = new ApiResponse<>(reservations, "success", "Reservas obtenidas satisfactoriamente");
            return ResponseEntity.ok(response);
        } else {
            response = new ApiResponse<>(null, "error", "Reservas no encontradas");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    @PutMapping("/reservation/{id}")
    public ResponseEntity<ApiResponse<Reservation>> updateReservation(@PathVariable Integer id, @RequestBody Reservation updatedReservation) {
        // Obtener la reserva existente por su ID
        Reservation existingReservation = reservationService.getReservationById(id);

        // Verificar si la reserva existe
        if (existingReservation == null) {
            // Si no se encuentra la reserva, devolver un error 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Actualizar los campos de la reserva con los datos proporcionados en updatedReservation
        existingReservation.setReservationNumber(updatedReservation.getReservationNumber());
        existingReservation.setDateFormatted(updatedReservation.getDateFormatted());
        existingReservation.setTicketId(updatedReservation.getTicketId());

        // Obtener el ID del vuelo desde la reserva actualizada
        Integer flightId = updatedReservation.getFlightId();

        // Buscar el vuelo por su ID
        Flight flight = flightService.getFlightById(flightId);

        // Verificar si se encontró el vuelo
        if (flight == null) {
            // Si no se encuentra el vuelo, devolver un error 400
            ApiResponse<Reservation> errorResponse = new ApiResponse<>(null, "error", "Vuelo no encontrado con ID: " + flightId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        // Asignar el vuelo a la reserva
        existingReservation.setFlight(flight);

        // Actualizar la reserva en la base de datos
        ApiResponse<Reservation> updatedEntity = reservationService.updateReservation(id, existingReservation);

        // Crear la respuesta
        ApiResponse<Reservation> response = updatedEntity;

        // Devolver una respuesta exitosa con la reserva actualizada
        return ResponseEntity.ok(response);
    }

}
