package com.alimert.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Component
public class JwtService {
    public static final String SECRET_KEY = "G1AlHs7FX0689MK3hvG/CV23uqS6V2P9mQ3+0wkhrGw=";

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("role", "ADMIN");

        return Jwts.builder().setSubject(userDetails.getUsername()).setIssuedAt(new Date())
                .addClaims(claimsMap)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2))// 2 hours
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public Object getClaimsByKey(String token, String key) {
        Claims claims = getClaims(token);
        return claims.get(key);
    }

    public Claims getClaims(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
        return claims;
    }

    public <T> T exportToken(String token, Function<Claims, T> claimsTFunction) {
        Claims claims = getClaims(token);

        return claimsTFunction.apply(claims);
    }

    public Key getKey() {
        byte[] decode = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(decode);
    }

    public String getUserNameByToken(String token) { // resolve token and catch username
        return exportToken(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        Date expiredDate = exportToken(token, Claims::getExpiration);
        return new Date().before(expiredDate);
    }
}
