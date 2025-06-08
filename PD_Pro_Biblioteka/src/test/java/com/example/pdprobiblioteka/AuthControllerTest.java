package com.example.pdprobiblioteka;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.pdprobiblioteka.control.AuthController;
import com.example.pdprobiblioteka.service.JwtService;
import com.example.pdprobiblioteka.service.SupabaseAdminDetailsService;
import com.example.pdprobiblioteka.service.SupabaseUserDetailsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Testy Klasy AuthController.
 */
@DisplayName("Testy AuthController")
@WebMvcTest(controllers = AuthController.class,
    excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class AuthControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private AuthenticationManager authenticationManager;

  @MockitoBean
  private JwtService jwtService;

  @MockitoBean
  private SupabaseUserDetailsService userDetailsService;

  @MockitoBean
  private SupabaseAdminDetailsService adminDetailsService;

  @Nested
  @DisplayName("Testy logowania użytkownika")
  class LoginUserTest {

    @Test
    @DisplayName("Poprawne logowanie użytkownika")
    void loginUserSuccess() throws Exception {
      String username = "user";
      String password = "pass";
      String token = "mocked.jwt.token";

      UserDetails mockUserDetails = Mockito.mock(UserDetails.class);

      Mockito.when(userDetailsService.loadUserByUsername(username)).thenReturn(mockUserDetails);
      Mockito.when(jwtService.generateToken(mockUserDetails)).thenReturn(token);

      String requestBody = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";

      mockMvc.perform(post("/api/auth/login")
              .contentType(MediaType.APPLICATION_JSON)
              .content(requestBody))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.token").value(token));
    }

    @Test
    @DisplayName("Niepoprawne logowanie użytkownika")
    void loginUserFailure() throws Exception {
      String username = "user";
      String password = "'wrongpass'";

      Mockito.doThrow(new BadCredentialsException("Bad credentials"))
          .when(authenticationManager)
          .authenticate(Mockito.any());

      String requestBody = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";

      mockMvc.perform(post("/api/auth/login")
              .contentType(MediaType.APPLICATION_JSON)
              .content(requestBody))
          .andExpect(status().isUnauthorized())
          .andExpect(content().string("Invalid login or password"));
    }
  }

  @Nested
  @DisplayName("Testy logowania administratora")
  class LoginAdminTest {

    @Test
    @DisplayName("Poprawne logowanie administratora")
    void loginAdminSuccess() throws Exception {
      String username = "admin";
      String password = "adminpass";
      String token = "admin.jwt.token";

      UserDetails mockAdminDetails = Mockito.mock(UserDetails.class);

      Mockito.when(adminDetailsService.loadUserByUsername(username)).thenReturn(mockAdminDetails);
      Mockito.when(jwtService.generateToken(mockAdminDetails)).thenReturn(token);

      String requestBody = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";

      mockMvc.perform(post("/api/auth/loginadmin")
              .contentType(MediaType.APPLICATION_JSON)
              .content(requestBody))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.token").value(token));
    }

    @Test
    @DisplayName("Niepoprawne logowanie administratora")
    void loginAdminFailure() throws Exception {
      String username = "admin";
      String password = "wrongpass";

      Mockito.doThrow(new BadCredentialsException("Bad credentials"))
          .when(authenticationManager)
          .authenticate(Mockito.any());

      String requestBody = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";

      mockMvc.perform(post("/api/auth/loginadmin")
              .contentType(MediaType.APPLICATION_JSON)
              .content(requestBody))
          .andExpect(status().isUnauthorized())
          .andExpect(content().string("Invalid login or password"));
    }
  }
}
