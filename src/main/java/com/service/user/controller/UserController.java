package com.service.user.controller;

import com.service.user.model.UserDto;
import com.service.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Create User
    @PostMapping
    public UserDto createUser(@RequestBody UserDto request) throws Exception {
        return userService.createUser(request);
    }

    // Get All Users
    @GetMapping("/all")
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers();
    }

    // Get User by ID
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    // Update User
    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody UserDto request) {
        return userService.updateUser(id, request);
    }

    // Delete User
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }
}
