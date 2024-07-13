package com.alurachallenge.forohub.services;

import com.alurachallenge.forohub.dto.RegisterDTO;
import com.alurachallenge.forohub.models.User;
import com.alurachallenge.forohub.repositories.UserRepository;
import com.alurachallenge.forohub.utils.customExceptions.AlreadyExistsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User registerUser(User user) {
        List<User> usuarios = userRepository.findAllByEmailOrUsername( user.getEmail(), user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(usuarios.isEmpty()) return userRepository.save(user);

        if(usuarios.stream().anyMatch(u -> u.getEmail().equals(user.getEmail()))) throw new AlreadyExistsException("Email already in use");
        if(usuarios.stream().anyMatch(u -> u.getUsername().equals(user.getUsername()))) throw new AlreadyExistsException("Username already in use");
        return null;
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);

    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
