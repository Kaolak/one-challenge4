package com.alurachallenge.forohub.dto;

import com.alurachallenge.forohub.models.Role;
import com.alurachallenge.forohub.models.User;

public record UserDTO(
        Long id,
        String username,
        String name,
        String email,
        Role role
) {
    public UserDTO (User user){
        this(user.getId(), user.getUsername(), user.getName(), user.getEmail(), user.getRol());
    }
}
