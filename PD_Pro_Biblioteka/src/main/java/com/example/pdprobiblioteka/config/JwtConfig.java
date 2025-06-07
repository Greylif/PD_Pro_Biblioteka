package com.example.pdprobiblioteka.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Klasa konfiguracji dla parametrów dostępu do JSON Web Token.
 * Wczytuje wartości z pliku application.properties
 * Zawiera klucz dla usługi JSON Web Token.
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "jwt")
@Component
public class JwtConfig {
  private String key;
}

