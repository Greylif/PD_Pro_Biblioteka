package com.example.pdprobiblioteka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Główna klasa aplikacji bibliotecznej.
 * Uruchamia aplikację Spring Boot z włączonym harmonogramowaniem zadań.
 */
@SpringBootApplication
@EnableScheduling
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
