package com.airplane.ticket.controller;

import com.airplane.ticket.entity.ApiResponse;
import com.airplane.ticket.entity.Reservation;
import com.airplane.ticket.entity.User;
import com.airplane.ticket.repository.ReservationRepository;
import com.airplane.ticket.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/airline")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;
    private final ReservationRepository reservationRepository;
    private static final Logger logger = Logger.getLogger(ReservationController.class.getName());
@PostMapping
public ResponseEntity<ApiResponse<User>> create(@RequestBody User user) {
    User newUser = userService.createUser(user);
    ApiResponse<User> response = new ApiResponse<>(newUser, "success", "Usuario y reserva creados satisfactoriamente");
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
}

    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers(){
        logger.info("Llamada al endpoint GET /restaurant recibida.");
        List<User> users = userService.getAllUsers();
        ApiResponse<List<User>> response;
        if (!users.isEmpty()){
            response = new ApiResponse<>(users, "success", "Usuarios obtenidos satisfactoriamente");
            return ResponseEntity.ok(response);
        } else {
            // Cambia null por una lista vacía
            response = new ApiResponse<>(new ArrayList<>(), "error", "Usuarios no encontrados");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(@RequestBody User updatedUser, @PathVariable Integer id) {
        logger.info("Llamada al endpoint PUT /restaurant recibida.");
        // Actualiza el usuario en el servicio
        User updatedUserEntity = userService.updateUser(id, updatedUser);
        // Verifica si se actualizó correctamente
        if (updatedUserEntity != null) {
            // Crea una respuesta con el usuario actualizado
            ApiResponse<User> response = new ApiResponse<>(updatedUserEntity, "success", "Usuario modificado satisfactoriamente");
            // Devuelve una respuesta OK con la respuesta creada
            return ResponseEntity.ok(response);
        } else {
            // Si el usuario no se encontró, crea una respuesta de error
            ApiResponse<User> response = new ApiResponse<>(null, "error", "Usuario no encontrado");
            // Devuelve una respuesta con estado NOT_FOUND y la respuesta de error
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

}
