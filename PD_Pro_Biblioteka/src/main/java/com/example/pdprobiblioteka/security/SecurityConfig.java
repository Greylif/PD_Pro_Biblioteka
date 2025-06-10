package com.example.pdprobiblioteka.security;

import com.example.pdprobiblioteka.service.JwtService;
import com.example.pdprobiblioteka.service.SupabaseAdminDetailsService;
import com.example.pdprobiblioteka.service.SupabaseUserDetailsService;
import jakarta.servlet.Filter;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Konfiguracja bezpieczeństwa aplikacji Spring Security. Definiuje uprawnienia, dostęp do
 * endpointów oraz sposób autoryzacji JWT.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private static final String ADMIN = "ADMIN";
  private static final String USER = "USER";


  /**
   * Definicja głównego łańcucha zabezpieczeń.
   *
   * @param http                    obiekt konfiguracji HTTP
   * @param jwtAuthenticationFilter filtr JWT do uwierzytelniania
   * @param authManager             menedżer uwierzytelniania
   * @return skonfigurowany filtr bezpieczeństwa
   * @throws Exception w przypadku błędów konfiguracji
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http,
      Filter jwtAuthenticationFilter,
      AuthenticationManager authManager) throws Exception {

    http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/auth/loginadmin").permitAll()
            .requestMatchers(HttpMethod.POST, "/library/uzytkownicy").permitAll()
            .requestMatchers(HttpMethod.POST, "/library/admini").permitAll()

            .requestMatchers(HttpMethod.GET, "/library/placowki").hasAnyRole(USER, ADMIN)
            .requestMatchers(HttpMethod.GET, "/library/wypozyczenia/{id}").hasAnyRole(USER, ADMIN)
            .requestMatchers(HttpMethod.GET, "/library/kary/{id}").hasAnyRole(USER, ADMIN)
            .requestMatchers(HttpMethod.GET, "/library/ksiazki").hasAnyRole(USER, ADMIN)
            .requestMatchers(HttpMethod.GET, "/library/ksiazki/filtr").hasAnyRole(USER, ADMIN)
            .requestMatchers(HttpMethod.GET, "/library/autorzy").hasAnyRole(USER, ADMIN)
            .requestMatchers(HttpMethod.POST, "/api/auth/setup-totp").hasAnyRole(USER, ADMIN)
            .requestMatchers(HttpMethod.POST, "/api/auth/confirm-totp").hasAnyRole(USER, ADMIN)
            .requestMatchers(HttpMethod.PUT, "/library/uzytkownicy/{id}").hasAnyRole(USER, ADMIN)
            .requestMatchers(HttpMethod.DELETE, "/library/uzytkownicy/{id}").hasAnyRole(USER, ADMIN)
            .requestMatchers(HttpMethod.PUT, "/library/uzytkownicy/passwordreset/{email}")
            .hasAnyRole(USER, ADMIN)

            .requestMatchers("/library/**").hasRole(ADMIN)
            .requestMatchers("/api/**").hasRole(ADMIN)

            .anyRequest().authenticated()
        )
        .sessionManagement(sess -> sess
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .authenticationManager(authManager)
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  /**
   * Konfiguracja menedżera uwierzytelniania z dwoma providerami: dla użytkowników i
   * administratorów.
   *
   * @param userDetailsService  serwis użytkownika
   * @param adminDetailsService serwis administratora
   * @return menedżer uwierzytelniania
   */
  @Bean
  public AuthenticationManager authenticationManager(
      SupabaseUserDetailsService userDetailsService,
      SupabaseAdminDetailsService adminDetailsService
  ) {
    DaoAuthenticationProvider userProvider = new DaoAuthenticationProvider();
    userProvider.setUserDetailsService(userDetailsService);
    userProvider.setPasswordEncoder(passwordEncoder());

    DaoAuthenticationProvider adminProvider = new DaoAuthenticationProvider();
    adminProvider.setUserDetailsService(adminDetailsService);
    adminProvider.setPasswordEncoder(passwordEncoder());

    return new org.springframework.security.authentication.ProviderManager(
        List.of(adminProvider, userProvider));
  }

  /**
   * Kodowanie haseł.
   *
   * @return domyślny encoder haseł
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Tworzy filtr JWT do wstrzyknięcia do łańcucha filtrów Spring Security.
   *
   * @param jwtService          serwis JWT
   * @param userDetailsService  serwis danych użytkownika
   * @param adminDetailsService serwis danych administratora
   * @return instancja filtra
   */
  @Bean
  public Filter jwtAuthenticationFilter(JwtService jwtService,
      SupabaseUserDetailsService userDetailsService,
      SupabaseAdminDetailsService adminDetailsService) {
    return new JwtAuthenticationFilter(jwtService, userDetailsService, adminDetailsService);
  }

}
