package com.example.pdprobiblioteka.tests;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Konfiguracja bezpieczeństwa wykorzystywana w testach. Ta konfiguracja wyłącza zabezpieczenia
 * Spring Security (np. CSRF) i pozwala na dostęp do wszystkich żądań bez autoryzacji. Ułatwia
 * testowanie aplikacji bez konieczności logowania się.
 */
@TestConfiguration
public class TestSecurityConfig {

  /**
   * Definicja łańcucha filtrów zabezpieczeń na potrzeby testów. Wyłącza CSRF i pozwala na dostęp do
   * wszystkich endpointów.
   *
   * @param http konfiguracja bezpieczeństwa HTTP
   * @return SecurityFilterChain z wyłączoną autoryzacją
   * @throws Exception w przypadku błędu konfiguracji
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(authz -> authz.anyRequest().permitAll());
    return http.build();
  }
}