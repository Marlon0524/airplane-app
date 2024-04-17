package com.airplane.ticket.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reservation {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer reservationNumber;
    private String dateFormatted; // Campo de fecha en formato de cadena
    private Date date; // Campo de fecha como objeto Date
    private Integer ticketId;
    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;
    // Suponiendo que tienes un método getFlight en la clase Reservation que devuelve el objeto Flight asociado a la reserva

    public Integer getFlightId() {
        if (this.flight != null) {
            return this.flight.getFlight_id();
        } else {
            return null;
        }
    }

    public void setStartDateFormatted(String startDateFormatted) {
        // Validar el formato de la fecha
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            // Intenta parsear la fecha
            this.date = sdf.parse(startDateFormatted);
        } catch (ParseException e) {
            // Si hay un error de formato, lanza una excepción
            throw new IllegalArgumentException("Formato de fecha inválido. El formato debe ser dd/MM/yyyy HH:mm:ss");
        }
        // Si la fecha se parsea correctamente, establece el valor del campo startDateFormatted
        this.dateFormatted = startDateFormatted;
    }

}
