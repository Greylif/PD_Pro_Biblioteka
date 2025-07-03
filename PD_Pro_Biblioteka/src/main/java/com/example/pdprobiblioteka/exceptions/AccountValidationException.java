package com.example.pdprobiblioteka.exceptions;

import lombok.Getter;

/**
 * Wyjątek zgłaszany, gdy walidacja konta zakończy się niepowodzeniem.
 */
@Getter
public class AccountValidationException extends RuntimeException {

  private final String field;

  /**
   * Tworzy nowy wyjątek AccountValidationException.
   *
   * @param field   nazwa niepoprawnego pola
   * @param message opis błędu walidacji
   */
  public AccountValidationException(String field, String message) {
    super(message);
    this.field = field;
  }
}
