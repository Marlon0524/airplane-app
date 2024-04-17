package com.airplane.ticket.service;

import com.airplane.ticket.entity.User;
import com.airplane.ticket.repository.UserRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private final UserRespository userRespository;

    public User createUser (User user){
    return userRespository.save(user);
    }
    public List<User> getAllUsers(){
        return userRespository.findAll();
    }
    public User updateUser(Integer id, User updatedUser) {
        User existingUser = userRespository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setDocumentNumber(updatedUser.getDocumentNumber());
        return userRespository.save(existingUser);
    }


}
