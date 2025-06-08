package com.example.pdprobiblioteka;

import com.example.pdprobiblioteka.security.JwtAuthenticationFilter;
import com.example.pdprobiblioteka.service.JwtService;
import com.example.pdprobiblioteka.service.SupabaseAdminDetailsService;
import com.example.pdprobiblioteka.service.SupabaseUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Klasa pomocnicza do testow, rozszerzająca JwtAuthenticationFilter w celu
 * umożliwienia testowania metody doFilterInternal.
 */
public class JwtAuthenticationFiltertestAid extends JwtAuthenticationFilter {

  /**
   * Konstruktor klasy.
   *
   * @param jwtService serwis obsługujący operacje związane z JWT
   * @param userDetailsService serwis do ładowania danych użytkownika z Supabase
   * @param adminDetailsService serwis do ładowania danych administratora z Supabase
   */
  public JwtAuthenticationFiltertestAid(JwtService jwtService,
      SupabaseUserDetailsService userDetailsService,
      SupabaseAdminDetailsService adminDetailsService) {
    super(jwtService, userDetailsService, adminDetailsService);
  }

  /**
   * Ręczne wywołanie metody doFilterInternal z klasy nadrzędnej.
   *
   * @param request żądanie HTTP
   * @param response odpowiedz HTTP
   * @param chain łańcuch filtrów
   * @throws ServletException wyjatek wyrzucany w przypadku błędu serwletu
   * @throws IOException wyjatek wyrzucany w przypadku błędu wejścia/wyjścia
   */
  public void invokeDoFilter(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain)
      throws ServletException, IOException {
    super.doFilterInternal(request, response, chain);
  }
}
