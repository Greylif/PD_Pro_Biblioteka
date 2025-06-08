package com.example.pdprobiblioteka;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.pdprobiblioteka.service.JwtService;
import com.example.pdprobiblioteka.service.SupabaseAdminDetailsService;
import com.example.pdprobiblioteka.service.SupabaseUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@DisplayName("JwtAuthenticationFilter Testy")
class JwtAuthenticationFilterTest {

  private JwtService jwtService;
  private SupabaseUserDetailsService userDetailsService;
  private SupabaseAdminDetailsService adminDetailsService;
  private JwtAuthenticationFiltertestAid jwtFilter;

  private FilterChain filterChain;
  private MockHttpServletRequest request;
  private MockHttpServletResponse response;

  @BeforeEach
  void setUp() {
    jwtService = mock(JwtService.class);
    userDetailsService = mock(SupabaseUserDetailsService.class);
    adminDetailsService = mock(SupabaseAdminDetailsService.class);
    jwtFilter = new JwtAuthenticationFiltertestAid(jwtService, userDetailsService,
        adminDetailsService);

    filterChain = mock(FilterChain.class);
    request = new MockHttpServletRequest();
    response = new MockHttpServletResponse();
  }

  @AfterEach
  void clearSecurityContext() {
    SecurityContextHolder.clearContext();
  }

  @Test
  @DisplayName("Brak nagłówka Authorization – filtr przepuszcza dalej")
  void noAuthHeader() throws ServletException, IOException {
    jwtFilter.invokeDoFilter(request, response, filterChain);

    verify(filterChain, times(1)).doFilter(request, response);
    assertNull(request.getUserPrincipal());
  }

  @Test
  @DisplayName("Bledny nagłówek Authorization – filtr przepuszcza dalej")
  void wrongAuthHeader() throws ServletException, IOException {
    String jwt = "valid.jwt.token";
    request.addHeader("Authorization", "Not Bearer " + jwt);

    jwtFilter.invokeDoFilter(request, response, filterChain);

    verify(filterChain, times(1)).doFilter(request, response);
    assertNull(request.getUserPrincipal());
  }

  @Test
  @DisplayName("Poprawny token użytkownika – ustawia kontekst Spring Security")
  void validUserToken() throws ServletException, IOException {
    String jwt = "valid.jwt.token";
    String username = "user123";

    request.addHeader("Authorization", "Bearer " + jwt);

    when(jwtService.extractUsername(jwt)).thenReturn(username);
    when(jwtService.extractRole(jwt)).thenReturn("ROLE_USER");

    UserDetails userDetails = new User(username, "pass", List.of(() -> "ROLE_USER"));
    when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
    when(jwtService.isTokenValid(jwt, userDetails)).thenReturn(true);

    jwtFilter.invokeDoFilter(request, response, filterChain);

    assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    assertEquals(username,
        SecurityContextHolder.getContext().getAuthentication().getName());

    verify(filterChain, times(1)).doFilter(request, response);
  }

  @Test
  @DisplayName("Token bez nazwy użytkownika – filtr przepuszcza dalej")
  void tokenWithoutUsername() throws ServletException, IOException {
    String jwt = "some.jwt.token";
    request.addHeader("Authorization", "Bearer " + jwt);

    when(jwtService.extractUsername(jwt)).thenReturn(null);

    jwtFilter.invokeDoFilter(request, response, filterChain);

    assertNull(SecurityContextHolder.getContext().getAuthentication());
    verify(filterChain, times(1)).doFilter(request, response);
  }

  @Test
  @DisplayName("Użytkownik już uwierzytelniony – filtr nie wykonuje ponownej autoryzacji")
  void userAlreadyAuthenticated() throws ServletException, IOException {
    String jwt = "some.jwt.token";
    String username = "user123";
    request.addHeader("Authorization", "Bearer " + jwt);

    when(jwtService.extractUsername(jwt)).thenReturn(username);
    when(jwtService.extractRole(jwt)).thenReturn("ROLE_USER");

    var userDetails = new User(username, "pass", List.of(() -> "ROLE_USER"));

    SecurityContextHolder.getContext().setAuthentication(
        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
    );

    jwtFilter.invokeDoFilter(request, response, filterChain);

    verify(filterChain, times(1)).doFilter(request, response);
  }


  @Test
  @DisplayName("Poprawny token admina – ustawia kontekst Spring Security")
  void validAdminToken() throws ServletException, IOException {
    String jwt = "admin.jwt.token";
    String username = "adminUser";

    request.addHeader("Authorization", "Bearer " + jwt);

    when(jwtService.extractUsername(jwt)).thenReturn(username);
    when(jwtService.extractRole(jwt)).thenReturn("ROLE_ADMIN");

    UserDetails adminDetails = new User(username, "pass", List.of(() -> "ROLE_ADMIN"));
    when(adminDetailsService.loadUserByUsername(username)).thenReturn(adminDetails);
    when(jwtService.isTokenValid(jwt, adminDetails)).thenReturn(true);

    jwtFilter.invokeDoFilter(request, response, filterChain);

    assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    assertEquals(username,
        SecurityContextHolder.getContext().getAuthentication().getName());

    verify(filterChain).doFilter(request, response);
  }

  @Test
  @DisplayName("Token nieważny – nie ustawia kontekstu")
  void invalidToken() throws ServletException, IOException {
    String jwt = "invalid.jwt.token";
    String username = "user";

    request.addHeader("Authorization", "Bearer " + jwt);

    when(jwtService.extractUsername(jwt)).thenReturn(username);
    when(jwtService.extractRole(jwt)).thenReturn("ROLE_USER");

    UserDetails userDetails = new User(username, "pass", List.of(() -> "ROLE_USER"));
    when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
    when(jwtService.isTokenValid(jwt, userDetails)).thenReturn(false);

    jwtFilter.invokeDoFilter(request, response, filterChain);

    assertNull(SecurityContextHolder.getContext().getAuthentication());
    verify(filterChain).doFilter(request, response);
  }
}
