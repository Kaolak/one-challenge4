package com.alurachallenge.forohub.controller;

import com.alurachallenge.forohub.dto.NewRoleDTO;
import com.alurachallenge.forohub.models.Role;
import com.alurachallenge.forohub.services.RoleService;
import com.alurachallenge.forohub.utils.customExceptions.AlreadyExistsException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController (RoleService roleService){
        this.roleService=roleService;
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody @Valid NewRoleDTO role){
        try {
        return ResponseEntity.ok(roleService.createRole(new Role(role)));
        }catch (AlreadyExistsException e){
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.CONFLICT, e.getMessage());
        }
    }
}
