package com.bottomline.autocomplete.controller;


import com.bottomline.autocomplete.dto.SuggestionResponse;
import com.bottomline.autocomplete.service.AutoCompleteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AutoCompleteController {

    private final AutoCompleteService autoCompleteService;

    public AutoCompleteController(AutoCompleteService autoCompleteService) {
        this.autoCompleteService = autoCompleteService;
    }

    @GetMapping("/autocomplete")
    public ResponseEntity<SuggestionResponse> getSuggestions(
            @RequestParam String prefix,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        if (prefix == null || prefix.trim().isEmpty()) {
            throw new IllegalArgumentException("Prefix must not be empty");
        }

        if (page < 0) {
            throw new IllegalArgumentException("Page number cannot be negative");
        }

        if (size <= 0) {
            throw new IllegalArgumentException("Page size must be positive");
        }

        List<String> suggestions = autoCompleteService.getSuggestions(prefix, page, size);
        String message = suggestions.isEmpty() ? "No suggestions found" : "Suggestions retrieved successfully";
        SuggestionResponse response = new SuggestionResponse(suggestions, message);
        return ResponseEntity.ok(response);
    }
}
