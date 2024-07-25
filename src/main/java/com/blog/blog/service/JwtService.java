package com.blog.blog.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY="e4cd76a1947011033bdbe13a1d4c6e8572e01ef16527564df5911aba3ab143beb06e69bccacf66a3be244054682765342babc4cd6236d1c6a3169a16ef7e3c2e759386811ef482e31e651592c98c6d692150c28cfc5affc1a8cc295f00e398c6409384af0a40f104aa35ea3cafbd8807b080f10366c8d0958c4e5d8808d86fdc31f06a9ff1b012472b77c74eca7285a1274210d58ebc3d16337b9f0798f035d8bd97b18000aa89141fbe27993e0be0e22a026023daccb7e5a02befd1088003bc077e67cc39a8999abc4a4d8b9976fb7755b2ff60bde6dea2ec808074ca3d28de5712f5c98134c5add8d5eab52b8d1ee5c95193c8d5592fe85069687dd8fad4f6";

    private Set<String> blacklist = new HashSet<>();
    public String extractUserEmail(String token) {

        return extractClaim(token,Claims::getSubject);
    }

    public <T> T extractClaim(String token , Function<Claims,T> claimsResolver){
        final Claims claims =extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails)
    {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Objects> extraClaims,
            UserDetails userDetails
    ){
       return Jwts.builder()
               .setClaims(extraClaims)
               .setSubject(userDetails.getUsername())
               .setIssuedAt(new Date(System.currentTimeMillis()))
               .setExpiration(new Date(System.currentTimeMillis() + 1000 *60 *24 ))
               .signWith(getSignInKey())
               .compact();
    }

    public  boolean isTokenValid(String token , UserDetails userDetails)
    {
        final String userEmail=extractUserEmail(token);
        return userEmail.equals(userDetails.getUsername()) && ! isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);

    }

    public void addToBlacklist(String token) {
        blacklist.add(token);
    }

    public boolean isBlacklisted(String token) {
        return blacklist.contains(token);
    }

    public String extractTokenFromHeader(String authHeader) {
        // Logic to extract the token from the Authorization header
        // Example: "Bearer eyJhbGciOiJIUzI1NiJ9..."
        return authHeader.replace("Bearer ", "");
    }
}
