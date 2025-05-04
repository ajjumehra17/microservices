package com.service.user.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class LoginRequest {
    private String username;
    private String password;

}
