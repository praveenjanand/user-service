package com.group5.util;

import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {
	
	private static final String SECRET_KEY = "PraveenGiriShashankPraveenGiriShashankPraveenGiriShashank";
    private static final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds

    public static String generateJwtToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
