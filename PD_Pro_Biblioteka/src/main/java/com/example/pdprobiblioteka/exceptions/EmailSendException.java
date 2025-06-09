package com.example.pdprobiblioteka.exceptions;

/**
 * Wyjątek zgłaszany w przypadku błędu podczas wysyłania wiadomości e-mail.
 */
public class EmailSendException extends RuntimeException {

  /**
   * Tworzy nowy wyjątek EmailSendException.
   *
   * @param message opis błędu
   * @param cause przyczyna błędu
   */
  public EmailSendException(String message, Throwable cause) {
    super(message, cause);
  }
}