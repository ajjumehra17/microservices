package com.service.user.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "profiles", schema = "user_schema")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "encrypted_sym_key", nullable = false)
    private String encryptedSymKey;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
}
