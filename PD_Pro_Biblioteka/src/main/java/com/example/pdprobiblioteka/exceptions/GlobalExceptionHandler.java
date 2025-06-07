package com.example.pdprobiblioteka.exceptions;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Globalna obsługa wyjątków w aplikacji.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Obsługuje wyjątek InstanceNotFoundException (404 - nie znaleziono zasobu).
   */
  @ExceptionHandler(InstanceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<ErrorResponse> handleInstanceNotFoundException(
      InstanceNotFoundException ex) {
    ErrorResponse errorResponse = new ErrorResponse(
        HttpStatus.NOT_FOUND.value(),
        "Account Not Found",
        ex.getMessage()
    );
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  /**
   * Obsługuje wyjątek AccountValidationException (400 - błąd walidacji).
   */
  @ExceptionHandler(AccountValidationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorResponse> handleAccountValidationException(
      AccountValidationException ex) {
    ErrorResponse errorResponse = new ErrorResponse(
        HttpStatus.BAD_REQUEST.value(),
        "Validation Error",
        ex.getMessage()
    );
    Map<String, String> details = new HashMap<>();
    details.put("field", ex.getField());
    errorResponse.setDetails(details);
    return new ResponseEntity<>(errorResponse,
        HttpStatus.BAD_REQUEST);
  }

  /**
   * Obsługuje wyjątek SupabaseConnectionException (500 - błąd serwera).
   */
  @ExceptionHandler(SupabaseConnectionException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<ErrorResponse> handleSupabaseConnectionException(
      SupabaseConnectionException ex) {
    ErrorResponse errorResponse = new ErrorResponse(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        "Supabase operation Error",
        ex.getMessage()
    );
    return new ResponseEntity<>(errorResponse,
        HttpStatus.INTERNAL_SERVER_ERROR);
  }


  /**
   * Obsługuje wyjątek EmailSendException (500 - błąd serwera).
   */
  @ExceptionHandler(EmailSendException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<ErrorResponse> handleEmailSendException(EmailSendException ex) {
    ErrorResponse errorResponse = new ErrorResponse(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        "Email sending Error",
        ex.getMessage()
    );
    return new ResponseEntity<>(errorResponse,
        HttpStatus.INTERNAL_SERVER_ERROR);
  }


  /**
   * Obsługuje wyjątek JsonFileException (500 - błąd serwera).
   */
  @ExceptionHandler(JsonFileException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<ErrorResponse> handleJsonFileException(JsonFileException ex) {
    ErrorResponse errorResponse = new ErrorResponse(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        "Json Operation Error",
        ex.getMessage()
    );
    return new ResponseEntity<>(errorResponse,
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * Obsługuje wszystkie inne nieobsłużone wyjątki (500 - błąd wewnętrzny).
   */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<ErrorResponse> handleGeneralException(Exception
      ex) {
    ErrorResponse errorResponse = new ErrorResponse(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        "Internal Server Error",
        "An unexpected error occurred: " + ex.getMessage()
    );
    return new ResponseEntity<>(errorResponse,
        HttpStatus.INTERNAL_SERVER_ERROR);
  }
}