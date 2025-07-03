package com.example.pdprobiblioteka;

import com.example.pdprobiblioteka.config.JwtConfig;
import com.example.pdprobiblioteka.config.SupabaseConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Główna klasa aplikacji bibliotecznej. Uruchamia aplikację Spring Boot z włączonym
 * harmonogramowaniem zadań.
 */
@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({SupabaseConfig.class, JwtConfig.class})
public class PdProBibliotekaApplication {

  /**
   * Metoda główna uruchamiająca aplikację.
   *
   * @param args argumenty wejściowe
   */
  public static void main(String[] args) {
    SpringApplication.run(PdProBibliotekaApplication.class, args);
  }
}
