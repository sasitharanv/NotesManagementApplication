package com.notesManagement.demo.Jwt;
import com.notesManagement.demo.Model.User;
import com.notesManagement.demo.Repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class JwtTokenProvider {

    @Autowired
    private UserRepository userRepository;

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private long jwtExpirationMilliseconds;

    private Set<String> revokedTokens = new HashSet<>();
    // Generate JWT token
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByEmail(username);
        Date now = new Date();
        long userId=user.getUserId();

        // Determine expiration based on last password reset date
        Date expiryDate = new Date(now.getTime() + jwtExpirationMilliseconds);

        // Use explicit charset for encoding
        byte[] keyBytes = jwtSecret.getBytes(Charset.forName("UTF-8"));
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.builder()
                .setSubject(username)
                .claim("userId",userId)
                .claim("UserName",username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Get username from JWT token
    public String getUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes(Charset.forName("UTF-8"))))
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
    // Extract userId from JWT token
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes(Charset.forName("UTF-8"))))
                .build()
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.get("userId").toString());
    }


    // Validate JWT Token
    public boolean validateToken(String token) {
        try {
           Claims claims= Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes(Charset.forName("UTF-8"))))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            // Get expiration date from the claims
            Date expirationDate = claims.getExpiration();

            if(  revokedTokens.contains(token)){
                System.out.println("Token is Expired Because The User is Logged Out");
                return  false;
            }

            if (expirationDate != null && expirationDate.before(new Date())) {
                System.out.println("Expired JWT token: Token has expired");
                return false;
            }
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("Expired JWT token: " + e.getMessage());
            return false; // Explicitly handle expired JWT tokens
        } catch (JwtException | IllegalArgumentException e) {
            // Cover all other JwtExceptions and IllegalArgumentException
            System.out.println("Invalid JWT token: " + e.getMessage());
            return false;
        }
    }
    // Logout: Add token to the blacklist
    public void revokeToken(String token) {

        revokedTokens.add(token);
    }

    private  boolean isTokenRevoked(String  token){

        return  false;
    }

    // Check if the token has expired - This method is now optional
    // as JJWT's parseClaimsJws method automatically checks the token expiration.
    private boolean isTokenExpired(String token) {
        // Implementation not required if JJWT's automatic expiration check is relied upon
        return false;
    }

    // This method remains the same as it already uses Charset.forName("UTF-8")
    private SecretKey key() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(Charset.forName("UTF-8")));
    }
}
