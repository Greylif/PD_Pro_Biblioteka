package com.example.pdprobiblioteka.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * Klasa konfiguracji dla parametrów dostępu do API Supabase.
 * Wczytuje wartości z pliku application.properties
 * Zawiera klucze API oraz adres URL usługi Supabase.
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "supabase.api")
public class SupabaseConfig {
  private String key;
  private String key2;
  private String url;

}
