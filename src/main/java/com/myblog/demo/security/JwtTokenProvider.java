package com.myblog.demo.security;

import com.myblog.demo.exceptions.BlogAppException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.SignatureException;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private  String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private  int jwtExpirationInMs;

    public  String generateToken(Authentication authentication)
    {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + jwtExpirationInMs);

        String token = Jwts.builder().setSubject(username).setIssuedAt(new Date())
                .setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        return token;
    }

    public  String getTokenUsername(String token)
    {
//        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            Claims claims = Jwts.parser().setSigningKey(jwtSecret).build()
                    .parseSignedClaims(token).getPayload();

        return claims.getSubject();
    }

    public  boolean validateToken(String token)
    {
        try {
//            Jwts.parserBuilder().setSigningKey(jwtSecret).parseClaimsJws(token);

            Jwts.parser().setSigningKey(jwtSecret)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
        }
//        catch(SignatureException e)
//        {
//            throw new BlogAppException(HttpStatus.BAD_REQUEST, "firma jwt no válida");
//        }
        catch (MalformedJwtException e)
        {
            throw  new BlogAppException(HttpStatus.BAD_REQUEST, "Token jwt no válido");
        }
        catch (ExpiredJwtException e) {
            throw  new BlogAppException(HttpStatus.BAD_REQUEST, "Token jwt caducado");
        }
        catch (UnsupportedJwtException e) {
            throw  new BlogAppException(HttpStatus.BAD_REQUEST, "Token jwt no compatible");
        }
        catch (IllegalArgumentException e) {
            throw  new BlogAppException(HttpStatus.BAD_REQUEST, "La cadena claims jwt está vacía");
        }
    }

}
