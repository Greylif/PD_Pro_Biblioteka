package com.example.pdprobiblioteka.exceptions;


/**
 * Wyjątek zgłaszany podczas błędu operacji na pliku JSON.
 */
public class JsonFileException extends RuntimeException {

  /**
   * Tworzy wyjątek JsonFileException.
   *
   * @param message opis błędu
   * @param cause   przyczyna błędu
   */
  public JsonFileException(String message, Throwable cause) {
    super(message, cause);
  }
}