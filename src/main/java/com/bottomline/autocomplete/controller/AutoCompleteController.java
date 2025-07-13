package com.bottomline.autocomplete.controller;


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
    public ResponseEntity<List<String>> getSuggestions(@RequestParam String prefix) {
        if (prefix == null || prefix.trim().isEmpty()) {
            throw new IllegalArgumentException("Prefix must not be empty");
        }

        List<String> suggestions = autoCompleteService.getSuggestions(prefix);
        return ResponseEntity.ok(suggestions);
    }
}
