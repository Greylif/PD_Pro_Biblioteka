package com.example.pdprobiblioteka.exceptions;


/**
 * Wyjątek zgłaszany, gdy nie znaleziono rekordu w bazie danych.
 */
public class InstanceNotFoundException extends RuntimeException {

  /**
   * Tworzy nowy wyjątek z podaną wiadomością.
   *
   * @param message szczegółowa wiadomość o błędzie
   */
  public InstanceNotFoundException(String message) {
    super(message);
  }

  /**
   * Tworzy wyjątek z nazwą tabeli i wiadomością.
   *
   * @param name nazwa tabeli
   * @param message szczegółowa wiadomość o błędzie
   */
  public InstanceNotFoundException(String name, String message) {
    super("Blad odczytu z tabeli " + name + ": " + message);
  }
}