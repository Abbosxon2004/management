package uz.pdp.online.management.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import uz.pdp.online.management.entity.Roles;

import java.util.Date;
import java.util.Set;

@Component
public class JwtProvider {
    private static final long expireTime = 10_000_000;
    private static final String secret = "You don't need to know this code";

    public String generateToken(String username, Set<Roles> roles) {
        Date expiryDate = new Date(System.currentTimeMillis() + expireTime);
        return Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .claim("roles",roles)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

    }

    public  String getUsername(String token){
       return Jwts
                .parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}
