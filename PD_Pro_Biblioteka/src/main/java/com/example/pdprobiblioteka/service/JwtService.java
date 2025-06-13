package com.example.pdprobiblioteka.service;

import com.example.pdprobiblioteka.model.Admin;
import com.example.pdprobiblioteka.model.Uzytkownik;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.function.Function;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Serwis do obsługi JWT (JSON Web Tokenów), w tym ich generowania, weryfikacji i odczytywania
 * danych z tokena.
 */
@Service
public class JwtService {

  private final String secretKey;

  private final SupabaseClient supabaseClient;

  /**
   * Konstruktor klasy nadajacy wartość klucza do generowania tokenów Jwt.
   */
  public JwtService(SupabaseClient supabaseClient) {
    this.secretKey = System.getenv("JWT_KEY");
    this.supabaseClient = supabaseClient;
  }

  /**
   * Pobiera nazwę użytkownika z podanego tokena.
   *
   * @param token token JWT
   * @return nazwa użytkownika
   */
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * Wyciąga dowolne dane z tokena.
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
   * Parsuje i zwraca wszystkie dane zawarte w tokenie.
   *
   * @param token token JWT
   * @return obiekt Claims
   */
  public Claims extractAllClaims(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
  }

  /**
   * Generuje nowy token JWT na podstawie danych użytkownika.
   *
   * @param userDetails dane użytkownika
   * @return token JWT z rolą oraz datą ważności
   */
  public String generateToken(UserDetails userDetails, boolean isAdmin) {
    String role = userDetails.getAuthorities().stream()
        .findFirst()
        .map(GrantedAuthority::getAuthority)
        .orElse("ROLE_USER");

    String userId = null;

    if (isAdmin) {
      Admin admin = supabaseClient.getAdminByUsername(userDetails.getUsername());
      userId = String.valueOf(admin.getId());
    } else {
      Uzytkownik user = supabaseClient.getUserByUsername(userDetails.getUsername());
      userId = String.valueOf(user.getId());
    }

    long expirationMillis = isAdmin
        ? 1000L * 60 * 60 * 12
        : 1000L * 60 * 60;

    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + expirationMillis);

    return Jwts.builder()
        .setSubject(userDetails.getUsername())
        .claim("role", role)
        .claim("userId", userId)
        //.claim("isAdmin", isAdmin) Chyba można usunąć
        .setIssuedAt(now)
        .setExpiration(expiryDate)
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
  }

  /**
   * Sprawdza, czy token jest poprawny i nie wygasł.
   *
   * @param token       token JWT
   * @param userDetails dane użytkownika
   * @return true, jeśli token jest ważny i należy do danego użytkownika
   */
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  /**
   * Sprawdza, czy token wygasł.
   *
   * @param token token JWT
   * @return true, jeśli token jest nieważny
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
   * @return rola użytkownika
   */
  public String extractRole(String token) {
    return extractClaim(token, claims -> claims.get("role", String.class));
  }

}
