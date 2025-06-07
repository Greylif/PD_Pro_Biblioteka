package com.example.pdprobiblioteka.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.function.Function;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Serwis do obsługi JWT (JSON Web Tokenów), w tym ich generowania,
 * weryfikacji i odczytywania danych z tokena.
 */
@Service
public class JwtService {

  private static final String SECRET_KEY = "tajnyklucz";

  /**
   * Pobiera nazwę użytkownika (subject) z podanego tokena.
   *
   * @param token token JWT
   * @return nazwa użytkownika
   */
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * Wyciąga dowolne dane (claim) z tokena.
   *
   * @param token          token JWT
   * @param claimsResolver funkcja przetwarzająca obiekt Claims
   * @param <T>            typ zwracanych danych
   * @return wartość danego claimu
   */
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    return claimsResolver.apply(extractAllClaims(token));
  }

  /**
   * Parsuje i zwraca wszystkie dane (claims) zawarte w tokenie.
   *
   * @param token token JWT
   * @return obiekt Claims
   */
  public Claims extractAllClaims(String token) {
    return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
  }

  /**
   * Generuje nowy token JWT na podstawie danych użytkownika.
   *
   * @param userDetails dane użytkownika
   * @return token JWT z rolą oraz datą ważności
   */
  public String generateToken(UserDetails userDetails) {
    String role = userDetails.getAuthorities().stream()
        .findFirst()
        .map(GrantedAuthority::getAuthority)
        .orElse("ROLE_USER");

    return Jwts.builder()
        .setSubject(userDetails.getUsername())
        .claim("role", role)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
        .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
        .compact();
  }

  /**
   * Sprawdza, czy token jest poprawny i nie wygasł.
   *
   * @param token        token JWT
   * @param userDetails  dane użytkownika
   * @return true jeśli token jest ważny i należy do danego użytkownika
   */
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  /**
   * Sprawdza, czy token wygasł.
   *
   * @param token token JWT
   * @return true jeśli token jest nieważny
   */
  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  /**
   * Pobiera datę wygaśnięcia tokena.
   *
   * @param token token JWT
   * @return data wygaśnięcia
   */
  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }


  /**
   * Wyciąga rolę użytkownika z tokena JWT.
   *
   * @param token token JWT
   * @return rola użytkownika (np. ROLE_ADMIN)
   */
  public String extractRole(String token) {
    return extractClaim(token, claims -> claims.get("role", String.class));
  }

}
