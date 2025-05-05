package com.service.user.service;

import com.service.user.config.EncryptionUtil;
import com.service.user.entity.Profile;
import com.service.user.entity.User;
import com.service.user.model.UserDto;
import com.service.user.repository.ProfileRepository;
import com.service.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Create User
    public UserDto createUser(UserDto request) throws Exception {
        // Validate input
        if (request.getUsername() == null || request.getEmail() == null) {
            throw new IllegalArgumentException("Username, email, and password are required");
        }
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Create User
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Create Profile
        Profile profile = new Profile();
        profile.setUser(user);
        profile.setEncryptedSymKey(EncryptionUtil.generateSymmetricKey());
        profile.setFirstName(request.getFirstName());
        profile.setLastName(request.getLastName());
        user.setProfile(profile);


        userRepository.save(user);
        profileRepository.save(profile);


        return toDto(user);
    }


    public UserDto findById(Long id) {
        return userRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id));
    }

    // Read User by Username
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Read All Users
    public List<UserDto> findAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // Update User
    public UserDto updateUser(Long id, UserDto request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id));

        Profile profile = user.getProfile();
        if (request.getFirstName() != null) {
            profile.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            profile.setLastName(request.getLastName());
        }

        // Save
        userRepository.save(user);
        profileRepository.save(profile);

        return toDto(user);
    }

    // Delete User
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id));
        userRepository.delete(user);
    }

    // Convert User to UserDto
    private UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getProfile().getFirstName());
        dto.setLastName(user.getProfile().getLastName());
        return dto;
    }
}