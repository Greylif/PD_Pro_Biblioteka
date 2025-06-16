package com.example.pd_pro_biblioteka_client;

import com.example.pd_pro_biblioteka_client.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Testy wyjątków po stronie klienta")
class ExceptionTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    @DisplayName("Obsługa ResourceNotFoundException")
    void testHandleResourceNotFoundException() {
        Model model = mock(Model.class);
        String result = handler.handleResourceNotFoundException(new ResourceNotFoundException("Not found"), model);

        verify(model).addAttribute("errorTitle", "Resource Not Found");
        verify(model).addAttribute("errorMessage", "Not found");
        assertEquals("error", result);
    }

    @Test
    @DisplayName("Obsługa BadRequestException")
    void testHandleBadRequestException() {
        Model model = mock(Model.class);
        String result = handler.handleBadRequestException(new BadRequestException("Bad input"), model);

        verify(model).addAttribute("errorTitle", "Bad Request");
        verify(model).addAttribute("errorMessage", "Bad input");
        assertEquals("error", result);
    }

    @Test
    @DisplayName("Obsługa IllegalArgumentException")
    void testHandleIllegalArgumentException() {
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        Model model = mock(Model.class);

        String result = handler.handleIllegalArgumentException(new IllegalArgumentException("Wrong data"), model, redirectAttributes);

        verify(redirectAttributes).addFlashAttribute("errorMessage", "Wrong data");
        assertEquals("redirect:/account", result);
    }

    @Test
    @DisplayName("Obsługa InsufficientFundsException")
    void testHandleInsufficientFundsException() {
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        String result = handler.handleInsufficientFundsException(new InsufficientFundsException("No money"), redirectAttributes);

        verify(redirectAttributes).addFlashAttribute("errorMessage", "No money");
        assertEquals("redirect:/account", result);
    }

    @Test
    @DisplayName("Obsługa ClientException")
    void testHandleClientException() {
        Model model = mock(Model.class);
        String result = handler.handleClientException(new ClientException("Client failed"), model);

        verify(model).addAttribute("errorTitle", "com.example.pd_pro_biblioteka_client.controller.Client Error");
        verify(model).addAttribute("errorMessage", "Client failed");
        assertEquals("error", result);
    }

    @Test
    @DisplayName("Obsługa ServerException")
    void testHandleServerException() {
        Model model = mock(Model.class);
        String result = handler.handleServerException(new ServerException("Server down"), model);

        verify(model).addAttribute("errorTitle", "Server Error");
        verify(model).addAttribute("errorMessage", "Server down");
        assertEquals("error", result);
    }

    @Test
    @DisplayName("Obsługa Generic Exception")
    void testHandleGenericException() {
        Model model = mock(Model.class);
        Exception ex = new Exception("Unknown failure");

        String result = handler.handleGenericException(ex, model);

        verify(model).addAttribute("errorTitle", "Unexpected Error");
        verify(model).addAttribute("errorMessage", "An unexpected error occurred: Unknown failure");
        assertEquals("error", result);
    }
}
