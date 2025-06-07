package com.example.pdprobiblioteka.control;

import com.example.pdprobiblioteka.model.AuthRequest;
import com.example.pdprobiblioteka.model.AuthResponse;
import com.example.pdprobiblioteka.service.JwtService;
import com.example.pdprobiblioteka.service.SupabaseAdminDetailsService;
import com.example.pdprobiblioteka.service.SupabaseUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Kontroler odpowiedzialny za uwierzytelnianie użytkowników i administratorów.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;
  private final SupabaseUserDetailsService userDetailsService;
  private final SupabaseAdminDetailsService adminDetailsService;

  /**
   * Konstruktor klasy.
   *
   * @param authenticationManager komponent odpowiedzialny za uwierzytelnianie użytkowników.
   * @param jwtService serwis obsługujący generowanie i walidację tokenów JWT.
   * @param userDetailsService serwis odpowiedzialny za ładowanie danych urzytkownika.
   * @param adminDetailsService   serwis odpowiedzialny za ładowanie danych admina.
   */
  public AuthController(AuthenticationManager authenticationManager, JwtService jwtService,
      SupabaseUserDetailsService userDetailsService,
      SupabaseAdminDetailsService adminDetailsService) {
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
    this.userDetailsService = userDetailsService;
    this.adminDetailsService = adminDetailsService;
  }

  /**
   * Loguje użytkownika i zwraca token JWT po poprawnym uwierzytelnieniu.
   *
   * @param request dane logowania
   * @return odpowiedź z tokenem JWT lub błąd 401
   */
  @PostMapping("/login")
  public ResponseEntity<Object> login(@RequestBody AuthRequest request) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
      );
      var userDetails = userDetailsService.loadUserByUsername(request.getUsername());

      String jwt = jwtService.generateToken(userDetails);
      return ResponseEntity.ok(new AuthResponse(jwt));
    } catch (AuthenticationException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login or password");
    }

  }

  /**
   * Loguje administratora i zwraca token JWT po poprawnym uwierzytelnieniu.
   *
   * @param request dane logowania
   * @return odpowiedź z tokenem JWT lub błąd 401
   */
  @PostMapping("/loginadmin")
  public ResponseEntity<Object> loginAdmin(@RequestBody AuthRequest request) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
      );
      var adminDetails = adminDetailsService.loadUserByUsername(request.getUsername());
      String jwt = jwtService.generateToken(adminDetails);
      return ResponseEntity.ok(new AuthResponse(jwt));
    } catch (AuthenticationException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login or password");
    }
  }


}
