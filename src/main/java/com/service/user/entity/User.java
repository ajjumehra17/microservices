package com.service.user.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users", schema = "user_schema")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;

}
