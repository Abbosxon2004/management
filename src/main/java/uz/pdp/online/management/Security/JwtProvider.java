package uz.pdp.online.management.Security;

import io.jsonwebtoken.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import uz.pdp.online.management.Entity.Role;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

@Component
public class JwtProvider {
    private final long expiredTime = 1000 * 60 * 60 * 24;
    private final String secretKey = "Bu kalitdir.Nobody need to know it";


    public String generateToken(String username, Collection<? extends GrantedAuthority> roleSet) {
        Date expiredDate = new Date(System.currentTimeMillis() + expiredTime);

        String token = Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expiredDate)
                .claim("roles", roleSet)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
        return token;
    }

    public String getEmailFromToken(String token) {
        try {
            String email = Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            return email;
        } catch (Exception e) {
            return null;
        }
    }
}
