package com.example.pdprobiblioteka.security;

import com.example.pdprobiblioteka.exceptions.AccountValidationException;
import com.example.pdprobiblioteka.service.JwtService;
import com.example.pdprobiblioteka.service.SupabaseAdminDetailsService;
import com.example.pdprobiblioteka.service.SupabaseUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filtr JWT, który przechwytuje każde żądanie HTTP i sprawdza, czy zawiera poprawny token JWT.
 * Jeżeli token jest poprawny, ustawia użytkownika w kontekście bezpieczeństwa Springa.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final SupabaseUserDetailsService userDetailsService;
  private final SupabaseAdminDetailsService adminDetailsService;

  /**
   * Konstruktor filtra JWT.
   *
   * @param jwtService          serwis do obsługi tokenów JWT
   * @param userDetailsService  serwis wczytujący dane użytkownika (rola USER)
   * @param adminDetailsService serwis wczytujący dane administratora (rola ADMIN)
   */
  public JwtAuthenticationFilter(JwtService jwtService,
      SupabaseUserDetailsService userDetailsService,
      SupabaseAdminDetailsService adminDetailsService) {
    this.jwtService = jwtService;
    this.userDetailsService = userDetailsService;
    this.adminDetailsService = adminDetailsService;
  }


  /**
   * Metoda wykonująca filtrację żądania. Sprawdza nagłówek Authorization, odczytuje token JWT i
   * ustawia kontekst bezpieczeństwa, jeśli token jest ważny.
   *
   * @param request     żądanie HTTP
   * @param response    odpowiedź HTTP
   * @param filterChain łańcuch filtrów
   * @throws ServletException wyjątek servletu
   * @throws IOException      wyjątek wejścia/wyjścia
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    final String authHeader = request.getHeader("Authorization");
    final String jwt;
    final String username;
    final String role;

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }
    try {
      jwt = authHeader.substring(7);
      username = jwtService.extractUsername(jwt);
      role = jwtService.extractRole(jwt);



    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails;

      if ("ROLE_ADMIN".equals(role)) {
        userDetails = adminDetailsService.loadUserByUsername(username);
      } else {
        userDetails = userDetailsService.loadUserByUsername(username);
      }

      if (!jwtService.isTokenValid(jwt, userDetails)) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Invalid token");
        return;
      }

      if (jwtService.isTokenRevoked(jwt)) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Token blacklisted");
        return;
      }

      var authToken = new UsernamePasswordAuthenticationToken(
          userDetails, null, userDetails.getAuthorities()
      );
      authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authToken);


    }

    filterChain.doFilter(request, response);

    } catch (ExpiredJwtException e) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("Token expired");
    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("Invalid token");
    }
  }
}

