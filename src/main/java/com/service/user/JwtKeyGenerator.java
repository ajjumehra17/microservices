package com.service.user;


import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JwtKeyGenerator {
    public static void main(String[] args) {
        Key key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256); // generates 256-bit key
        String base64Key = Encoders.BASE64.encode(key.getEncoded());
        System.out.println("Your JWT Secret Key: " + base64Key);
    }
}
