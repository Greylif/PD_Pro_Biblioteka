package com.example.pdprobiblioteka.service;

import com.example.pdprobiblioteka.model.Uzytkownik;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Serwis do wczytywania danych użytkownika z bazy Supabase w celu uwierzytelnienia.
 */
@Service
public class SupabaseUserDetailsService implements UserDetailsService {

  private final SupabaseClient supabaseService;

  /**
   * Konstruktor przyjmujący zależność do klienta Supabase.
   *
   * @param supabaseService klient do komunikacji z bazą danych Supabase
   */
  public SupabaseUserDetailsService(SupabaseClient supabaseService) {
    this.supabaseService = supabaseService;
  }

  /**
   * Wczytuje dane użytkownika na podstawie nazwy użytkownika.
   *
   * @param username nazwa użytkownika
   * @return dane użytkownika zgodne z interfejsem
   * @throws UsernameNotFoundException gdy użytkownik o podanej nazwie nie istnieje
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Uzytkownik user = supabaseService.getUserByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("User not found: " + username);
    }
    return new org.springframework.security.core.userdetails.User(
        user.getNazwaUzytkownika(),
        user.getHaslo(),
        List.of(new SimpleGrantedAuthority("ROLE_USER"))
    );
  }

}
