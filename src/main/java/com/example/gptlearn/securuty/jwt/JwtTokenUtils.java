package com.example.gptlearn.securuty.jwt;

import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Log4j2
public class JwtTokenUtils {

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.lifetime}")
    private Duration lifetimeAccessToken;

    private final static String ROLES = "roles";

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        claims.put(ROLES, roles);
        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + lifetimeAccessToken.toMillis());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String getUsername(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validate( String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired " + token);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt " + token);
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt " + token);
        } catch (SignatureException sEx) {
            log.error("Invalid signature " + token);
        } catch (Exception e) {
            log.error("invalid token " + token);
        }
        return false;

    }
}
