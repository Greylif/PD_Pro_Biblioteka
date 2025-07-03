package com.example.pdprobiblioteka.service;

import com.example.pdprobiblioteka.model.Admin;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Serwis do wczytywania danych administratora z bazy Supabase w celu uwierzytelnienia.
 */
@Service
public class SupabaseAdminDetailsService implements UserDetailsService {

  private final SupabaseClient supabaseService;

  /**
   * Konstruktor przyjmujący zależność do klienta Supabase.
   *
   * @param supabaseService klient do komunikacji z bazą danych Supabase
   */
  public SupabaseAdminDetailsService(SupabaseClient supabaseService) {
    this.supabaseService = supabaseService;
  }

  /**
   * Wczytuje dane administratora na podstawie nazwy użytkownika.
   *
   * @param username nazwa użytkownika administratora
   * @return dane użytkownika zgodne z interfejsem
   * @throws UsernameNotFoundException gdy administrator o podanej nazwie nie istnieje
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Admin admin = supabaseService.getAdminByUsername(username);
    if (admin == null) {
      throw new UsernameNotFoundException("Admin not found: " + username);
    }
    return new org.springframework.security.core.userdetails.User(
        admin.getNazwaUzytkownika(),
        admin.getHaslo(),
        List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
    );
  }
}
