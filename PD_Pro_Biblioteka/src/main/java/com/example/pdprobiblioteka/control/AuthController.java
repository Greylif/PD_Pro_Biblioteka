package com.example.pdprobiblioteka.control;

import com.example.pdprobiblioteka.model.Admin;
import com.example.pdprobiblioteka.model.AuthRequest;
import com.example.pdprobiblioteka.model.AuthResponse;
import com.example.pdprobiblioteka.model.Uzytkownik;
import com.example.pdprobiblioteka.service.JwtService;
import com.example.pdprobiblioteka.service.SupabaseAdminDetailsService;
import com.example.pdprobiblioteka.service.SupabaseClient;
import com.example.pdprobiblioteka.service.SupabaseUserDetailsService;
import com.example.pdprobiblioteka.service.TotpService;
import java.util.Map;
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
 * Kontroler odpowiedzialny za uwierzytelnianie użytkowników i administratorów oraz za obsługę TOTP.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;
  private final SupabaseUserDetailsService userDetailsService;
  private final SupabaseAdminDetailsService adminDetailsService;
  private final SupabaseClient supabaseClient;
  private final TotpService totpService;

  /**
   * Konstruktor klasy.
   *
   * @param authenticationManager komponent odpowiedzialny za uwierzytelnianie użytkowników.
   * @param jwtService serwis obsługujący generowanie i walidację tokenów JWT.
   * @param userDetailsService serwis odpowiedzialny za ładowanie danych użytkownika.
   * @param adminDetailsService serwis odpowiedzialny za ładowanie danych admina.
   */
  public AuthController(AuthenticationManager authenticationManager, JwtService jwtService,
      SupabaseUserDetailsService userDetailsService,
      SupabaseAdminDetailsService adminDetailsService,
      SupabaseClient supabaseClient,
      TotpService totpService) {
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
    this.userDetailsService = userDetailsService;
    this.adminDetailsService = adminDetailsService;
    this.supabaseClient = supabaseClient;
    this.totpService = totpService;
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
      Uzytkownik user = supabaseClient.getUserByUsername(request.getUsername());

      if (Boolean.TRUE.equals(user.getMfaEnabled())) {
        String totp = request.getTotp();
        if (totp == null || totp.isEmpty()
            || !totpService.verifyCode(user.getMfaSecret(), Integer.parseInt(totp))) {
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
              .body("Invalid or missing TOTP code");
        }
      }

      String jwt = jwtService.generateToken(userDetails, false);

      /* To jest potrzebne do przyszłości jak odczytywać
      String secretKey = System.getenv("JWT_KEY");

      Claims claims = Jwts.parser()
          .setSigningKey(secretKey)
          .parseClaimsJws(jwt)
          .getBody();

      String username = claims.getSubject();
      String role = claims.get("role", String.class);
      String userId = claims.get("userId", String.class);
      Boolean isAdmin = claims.get("isAdmin", Boolean.class);
      System.out.println(username);
      System.out.println(role);
      System.out.println(userId);
      System.out.println(isAdmin);

 */

      return ResponseEntity.ok(new AuthResponse(jwt));
    } catch (AuthenticationException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login or password");
    } catch (NumberFormatException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("TOTP code must be numeric");
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
      Admin admin = supabaseClient.getAdminByUsername(request.getUsername());

      if (Boolean.TRUE.equals(admin.getMfaEnabled())) {
        String totp = request.getTotp();
        if (totp == null || totp.isEmpty()
            || !totpService.verifyCode(admin.getMfaSecret(), Integer.parseInt(totp))) {
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
              .body("Invalid or missing TOTP code");
        }
      }

      String jwt = jwtService.generateToken(adminDetails, true);
      return ResponseEntity.ok(new AuthResponse(jwt));
    } catch (AuthenticationException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login or password");
    } catch (NumberFormatException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("TOTP code must be numeric");
    }
  }



  /**
   * Włączenie Totp u użytkownika.
   *
   * @param request dane logowania w celu uwierzytelnienia
   * @return secret oraz ulr kodu QR w celu dodania 2FA przez klienta np. w telefonie
   */
  @PostMapping("/setup-totp")
  public ResponseEntity<Object> setupTotp(@RequestBody AuthRequest request) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
      );
      Uzytkownik user = supabaseClient.getUserByUsername(request.getUsername());

      if (Boolean.TRUE.equals(user.getMfaEnabled())) {
        return ResponseEntity.badRequest().body("TOTP already enabled");
      }

      String secret = totpService.generateSecretKey();
      String barcodeUrl = totpService.getQrBarcodeUrl(user.getNazwaUzytkownika(), secret);

      user.setMfaSecret(secret);
      supabaseClient.updateUserMfaSecret(user.getId(), secret);

      return ResponseEntity.ok(Map.of(
          "secret", secret,
          "qrCodeUrl", barcodeUrl
      ));
    } catch (AuthenticationException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
  }


  /**
   * Włączenie Totp u administratora.
   *
   * @param request dane logowania w celu uwierzytelnienia
   * @return secret oraz ulr kodu QR w celu dodania 2FA przez administratora np. w telefonie
   */
  @PostMapping("/setup-totp/admin")
  public ResponseEntity<Object> setupTotpAdmin(@RequestBody AuthRequest request) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
      );

      Admin admin = supabaseClient.getAdminByUsername(request.getUsername());
      if (admin == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found");
      }

      if (Boolean.TRUE.equals(admin.getMfaEnabled())) {
        return ResponseEntity.badRequest().body("TOTP already enabled");
      }

      String secret = totpService.generateSecretKey();
      String barcodeUrl = totpService.getQrBarcodeUrl(admin.getNazwaUzytkownika(), secret);

      admin.setMfaSecret(secret);
      supabaseClient.updateAdminMfaSecret(admin.getId(), secret);

      return ResponseEntity.ok(Map.of(
          "secret", secret,
          "qrCodeUrl", barcodeUrl
      ));
    } catch (AuthenticationException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
  }


  /**
   * Potwierdzenie założenia Totp przez użytkownika.
   *
   * @param payload dane zawierające nazwę użytkownika oraz kod
   *                pobrany z aplikacji uwierzytelniającej
   * @return wiadomość o błędzie lub, czy potwierdzenie przebiegło pomyślnie
   */
  @PostMapping("/confirm-totp")
  public ResponseEntity<Object> confirmTotp(@RequestBody Map<String, Object> payload) {
    String username = (String) payload.get("username");
    int code = (int) payload.get("code");

    Uzytkownik user = supabaseClient.getUserByUsername(username);
    if (user == null || user.getMfaSecret() == null) {
      return ResponseEntity.badRequest().body("TOTP not initialized");
    }

    if (!totpService.verifyCode(user.getMfaSecret(), code)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid TOTP code");
    }

    user.setMfaEnabled(true);
    supabaseClient.updateUserMfaEnabled(user.getId(), true);

    return ResponseEntity.ok("TOTP enabled successfully");
  }

  /**
   * Potwierdzenie założenia Totp przez administratora.
   *
   * @param payload dane zawierające nazwę użytkownika oraz kod
   *                pobrany z aplikacji uwierzytelniającej
   * @return wiadomość o błędzie lub, czy potwierdzenie przebiegło pomyślnie
   */
  @PostMapping("/confirm-totp/admin")
  public ResponseEntity<Object> confirmTotpAdmin(@RequestBody Map<String, Object> payload) {
    String username = (String) payload.get("username");
    int code = (int) payload.get("code");

    Admin admin = supabaseClient.getAdminByUsername(username);
    if (admin == null || admin.getMfaSecret() == null) {
      return ResponseEntity.badRequest().body("TOTP not initialized");
    }

    if (!totpService.verifyCode(admin.getMfaSecret(), code)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid TOTP code");
    }

    admin.setMfaEnabled(true);
    supabaseClient.updateAdminMfaEnabled(admin.getId(), true);

    return ResponseEntity.ok("TOTP enabled successfully");
  }

}
