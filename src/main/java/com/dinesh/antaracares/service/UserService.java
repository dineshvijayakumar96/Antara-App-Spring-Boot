package com.dinesh.antaracares.service;

import com.dinesh.antaracares.dao.UserRepository;
import com.dinesh.antaracares.entity.Roles;
import com.dinesh.antaracares.entity.UserPrincipal;
import com.dinesh.antaracares.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public Users register(Users user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public String verify(Users user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if (authentication.isAuthenticated()) {

            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

            var roles = userPrincipal.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            return jwtService.generateToken(user.getUsername(), roles);
        }

        return "Failed!";
    }

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public Users getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    public Users updateUser(Users updatedUser) {
        Users existingUser = userRepository.findById(updatedUser.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setPassword(updatedUser.getPassword());

        existingUser.getRoles().removeIf(existingRole ->
                updatedUser.getRoles().stream()
                        .noneMatch(newRole -> newRole.getRole().equals(existingRole.getRole()))
        );

        for (Roles newRole : updatedUser.getRoles()) {
            boolean exists = existingUser.getRoles().stream()
                    .anyMatch(existingRole -> existingRole.getRole().equals(newRole.getRole()));
            if (!exists) {
                newRole.setUser(existingUser);
                existingUser.getRoles().add(newRole);
            }
        }

        return userRepository.save(existingUser);
    }

    public void deleteUser(int id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with ID: " + id);
        }

        userRepository.deleteById(id);
    }
}
