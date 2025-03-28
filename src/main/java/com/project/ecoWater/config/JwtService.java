package com.project.ecoWater.config;

import com.project.ecoWater.user.domain.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final SecretKey SECRET_KEY= Jwts.SIG.HS256.key().build();

    private static final long JWT_TIME_REFRESH_VALIDATE = 1000 * 60  * 60 * 24;
    private static final long REFRESH_WINDOW = 1000 * 60 * 60 * 24 * 7;


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+JWT_TIME_REFRESH_VALIDATE))
                .signWith(getSignInKey(),Jwts.SIG.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

        } catch (ExpiredJwtException e) {
            return e.getClaims();
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            throw new RuntimeException("Invalid JWT token or mal formed", e);
        }
    }

    private SecretKey getSignInKey() {
        return SECRET_KEY;
    }

    public boolean canTokenBeRenewed(String token) {
        try {
            Claims claims = extractAllClaims(token);
            Date expiration = claims.getExpiration();
            long currentTime = System.currentTimeMillis();
            return expiration.before(new Date(currentTime)) &&
                    expiration.getTime() + REFRESH_WINDOW > currentTime;
        } catch (Exception e) {
            return false;
        }
    }

    public String renewToken(String token, UserDetails userDetails) {
        if (!canTokenBeRenewed(token)) {
            throw new IllegalArgumentException("The JWT couldn't be renewed");
        }
        return generateToken(userDetails);
    }
}
