package com.example.pdprobiblioteka.exceptions;


/**
 * Wyjątek zgłaszany w przypadku problemów z połączeniem do Supabase.
 */
public class SupabaseConnectionException extends RuntimeException {

  /**
   * Tworzy nowy wyjątek SupabaseConnectionException.
   *
   * @param message opis błędu
   * @param cause   przyczyna błędu
   */
  public SupabaseConnectionException(String message, Throwable cause) {
    super(message, cause);
  }
}