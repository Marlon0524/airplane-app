package com.airplane.ticket.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue
    private Integer id;
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    private String firstName;
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    private String lastName;
    private Integer documentNumber;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Reservation> reservations = new ArrayList<>();

}
