package com.service.user.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.service.user.entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<Object> findByEmail(String email);

}
