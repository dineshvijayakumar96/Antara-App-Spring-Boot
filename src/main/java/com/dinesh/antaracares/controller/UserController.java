package com.dinesh.antaracares.controller;

import com.dinesh.antaracares.entity.Users;
import com.dinesh.antaracares.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<Users>> getAllUsers() {
        List<Users> users = userService.getAllUsers();
        return ResponseEntity
                .ok()
                .header("Content-Range", "users 0-" + (users.size() - 1) + "/" + users.size())
                .body(users);
    }

    @PostMapping("/users")
    public Users createUser(@RequestBody Users user) {
        return userService.register(user);
    }

    // Get single user
    @GetMapping("/users/{id}")
    public Users getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    // Update user
    @PutMapping("/users/{id}")
    public Users updateUser(@PathVariable int id, @RequestBody Users updatedUser) {
        updatedUser.setId(id);
        return userService.updateUser(updatedUser);
    }

    // Delete user
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }

    @PostMapping("/login")
    public String login(@RequestBody Users user) {
        return userService.verify(user);
    }
}
