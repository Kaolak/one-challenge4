package com.alurachallenge.forohub.services;

import com.alurachallenge.forohub.models.Role;
import com.alurachallenge.forohub.repositories.RoleRepository;
import com.alurachallenge.forohub.utils.customExceptions.AlreadyExistsException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role createRole(Role role){
        roleRepository.findByName(role.getName()).ifPresent(r -> {
            throw new AlreadyExistsException("Role already exists");
        });
        return roleRepository.save(role);
    }

    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }
}
