package groupe_9_daimai.com.Daimai.Config;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwUtil {

    private static final String SECRET = "wX7@9$!bF3mZpK2hR8tV5qL6nJ1uO4cD7yP0xS2vA9eM6rB3hT8kL1qF4wU7dJ9g";
    private static final Key KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    private static final long DATE_EXPIRATION = 864_000_000;


    // Génération du token
    public String generateToken(String telephone) {
        return Jwts.builder()
                .setSubject(telephone)
                .setExpiration(new Date(System.currentTimeMillis() + DATE_EXPIRATION))
                .signWith(KEY, SignatureAlgorithm.HS512) //  Utilisation de Key
                .compact();
    }

   /* // Extraction du username/telephone
    public  String extraireTelephone(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Vérifier validité du token
    public boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(KEY)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }*/
}
