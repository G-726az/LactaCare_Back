package ista.M4A2.config.authenticator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import jakarta.annotation.PostConstruct;

@Component
public class JwtTokenProvider {

	@Value("${jwt.secret}")
    private String jwtSecret;
    
    @Value("${jwt.expiration}")
    private long jwtExpirationMs;
    
    @Value("${jwt.refresh-expiration}")
    private long jwtRefreshExpirationMs;
    
    private SecretKey signingKey;
    
    @PostConstruct
    public void init() {
        byte[] keyBytes = Base64.getDecoder().decode(jwtSecret);
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
    }
    private SecretKey getSigningKey() {
        return signingKey;
    }
    
    
     //Genera un access token JWT
     
    public String generateToken(Long userId, String userType, String rol, String correo) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
        
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("userType", userType); // PACIENTE o EMPLEADO
        claims.put("rol", rol); // PACIENTE, DOCTOR, ADMINISTRADOR
        claims.put("correo", correo);
        
        return Jwts.builder()
                .claims(claims)
                .subject(String.valueOf(userId))
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }
    
    
     // Genera un refresh token
     
    public String generateRefreshToken(Long userId, String userType) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtRefreshExpirationMs);
        
        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("userType", userType)
                .claim("type", "refresh")
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }
    
    
     //Obtiene el userId del token
     
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        
        return Long.valueOf(claims.getSubject());
    }
    
    
     //Obtiene el userType del token
     
    public String getUserTypeFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        
        return claims.get("userType", String.class);
    }
    
    
     //Obtiene el rol del token
     
    public String getRolFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        
        return claims.get("rol", String.class);
    }
    
  // Obtiene el correo del token

    public String getCorreoFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        
        return claims.get("correo", String.class);
    }
    
// Valida el token JWT

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Token inválido o expirado
            return false;
        }
    }
    

     //Obtiene el tiempo de expiración en segundos

    public long getExpirationInSeconds() {
        return jwtExpirationMs / 1000;
    }
}