package com.galeria.estilo.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(Model model) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("status", 500);
        errorDetails.put("error", "Internal Server Error");
        errorDetails.put("message", "An unexpected error occurred.");
        errorDetails.put("path", "N/A");
        errorDetails.put("timestamp", System.currentTimeMillis());

        model.addAttribute("errorDetails", errorDetails);

        return "Errores/Error";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNotFoundError(Model model) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("status", 404);
        errorDetails.put("error", "Not Found");
        errorDetails.put("message", "The requested page was not found.");
        errorDetails.put("path", "N/A");
        errorDetails.put("timestamp", System.currentTimeMillis());

        model.addAttribute("errorDetails", errorDetails);

        return "Errores/Error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericError(Exception ex, Model model) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("status", 500);
        errorDetails.put("error", "Internal Server Error");
        errorDetails.put("message", ex.getMessage());
        errorDetails.put("path", "N/A");
        errorDetails.put("timestamp", System.currentTimeMillis());

        model.addAttribute("errorDetails", errorDetails);

        return "Errores/Error";
    }
}