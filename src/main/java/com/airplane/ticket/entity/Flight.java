package com.airplane.ticket.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Flight {
    @Id
    @GeneratedValue
    private Integer flight_id;
    private String flightNumber;
    private String departureCity;
    private String arrivalCity;

    // Otros campos y métodos getters/setters
}
