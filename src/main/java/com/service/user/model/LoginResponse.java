package com.service.user.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class LoginResponse {
    private String token;
    private Long userId;
}
