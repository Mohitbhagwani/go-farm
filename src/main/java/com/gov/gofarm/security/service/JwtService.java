package com.gov.gofarm.security.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.gov.gofarm.exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

    @Autowired
    HttpServletRequest request;


    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        if(isTokenExpired(token)){
            throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "Token has expired, kindly regenerate it.");
        }
        if(!username.equals(userDetails.getUsername())){
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "User not found.");
        }
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    public String generateToken(Map<String,Object> claims){
        String userName = null;
        if (!claims.isEmpty()){
            userName= claims.get("contactNumber").toString();
        }
        else if (claims.isEmpty() || userName==null){
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "something went wrong while authenticating.");
        }
        return createToken(claims,userName);
    }

    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                //token expiry
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims userData(){
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
               return extractAllClaims(token);
            }
        return null;
    }

}
