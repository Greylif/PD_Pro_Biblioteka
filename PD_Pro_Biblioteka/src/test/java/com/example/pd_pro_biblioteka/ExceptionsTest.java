package com.example.pd_pro_biblioteka;

import com.example.pd_pro_biblioteka.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testy wyjatkow")
class ExceptionsTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }


        @Test
        @DisplayName("Test dzialania InstanceNotFoundException")
        void testInstanceNotFoundException() {
            InstanceNotFoundException ex = new InstanceNotFoundException("Account 123 not found");

            ResponseEntity<ErrorResponse> response = handler.handleInstanceNotFoundException(ex);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertEquals("Account Not Found", response.getBody().getError());
            assertEquals("Account 123 not found", response.getBody().getMessage());
        }

        @Test
        @DisplayName("Test dzialania AccountValidationException")
        void testAccountValidationException() {
            AccountValidationException ex = new AccountValidationException("email", "email");

            ResponseEntity<ErrorResponse> response = handler.handleAccountValidationException(ex);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertEquals("Validation Error", response.getBody().getError());
            assertEquals("email", response.getBody().getMessage());
            assertNotNull(response.getBody().getDetails());
            assertEquals("email", response.getBody().getDetails().get("field"));
        }

        @Test
        @DisplayName("Test dzialania SupabaseConnectionException")
        void testSupabaseConnectionException() {
            SupabaseConnectionException ex = new SupabaseConnectionException("Supabase operation Error", new RuntimeException("test error"));

            ResponseEntity<ErrorResponse> response = handler.handleSupabaseConnectionException(ex);

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
            assertEquals("Supabase operation Error", response.getBody().getError());
        }

        @Test
        @DisplayName("Test dzialania JsonFileException")
        void testJsonFileException() {
            JsonFileException ex = new JsonFileException("File error", new RuntimeException("test error"));

            ResponseEntity<ErrorResponse> response = handler.handleJsonFileException(ex);

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
            assertEquals("Json Operation Error", response.getBody().getError());
        }

        @Test
        @DisplayName("Test dzialania EmailSendException")
        void testEmailSendException() {
            EmailSendException ex = new EmailSendException("Email sending Error", new RuntimeException("test error"));

            ResponseEntity<ErrorResponse> response = handler.handleEmailSendException(ex);

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
            assertEquals("Email sending Error", response.getBody().getError());
        }

        @Test
        @DisplayName("Test dzialania GeneralException")
        void testGeneralException() {
            Exception ex = new Exception("Unexpected crash");

            ResponseEntity<ErrorResponse> response = handler.handleGeneralException(ex);

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
            assertEquals("Internal Server Error", response.getBody().getError());
            assertTrue(response.getBody().getMessage().contains("Unexpected crash"));
        }
}
