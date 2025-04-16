package com.example.pd_pro_biblioteka_client.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String ERRORMESSAGE = "errorMessage";
    private static final String ERRORTITLE = "errorTitle";
    private static final String ERROR = "error";

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleResourceNotFoundException(ResourceNotFoundException ex, Model model) {
        model.addAttribute(ERRORTITLE, "Resource Not Found");
        model.addAttribute(ERRORMESSAGE, ex.getMessage());
        return ERROR;
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadRequestException(BadRequestException ex, Model model) {
        model.addAttribute(ERRORTITLE, "Bad Request");
        model.addAttribute(ERRORMESSAGE, ex.getMessage());
        return ERROR;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(IllegalArgumentException ex, Model model, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(ERRORMESSAGE, ex.getMessage());
        return "redirect:/account";
    }

    @ExceptionHandler(InsufficientFundsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInsufficientFundsException(InsufficientFundsException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(ERRORMESSAGE, ex.getMessage());
        return "redirect:/account";
    }

    @ExceptionHandler(ClientException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleClientException(ClientException ex, Model model) {
        model.addAttribute(ERRORTITLE, "com.example.pd_pro_biblioteka_client.controller.Client Error");
        model.addAttribute(ERRORMESSAGE, ex.getMessage());
        return ERROR;
    }

    @ExceptionHandler(ServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleServerException(ServerException ex, Model model) {
        model.addAttribute(ERRORTITLE, "Server Error");
        model.addAttribute(ERRORMESSAGE, ex.getMessage());
        return ERROR;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGenericException(Exception ex, Model model) {
        model.addAttribute(ERRORTITLE, "Unexpected Error");
        model.addAttribute(ERRORMESSAGE, "An unexpected error occurred: " + ex.getMessage());
        return ERROR;
    }
}