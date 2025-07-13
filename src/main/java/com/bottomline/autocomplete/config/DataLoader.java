package com.bottomline.autocomplete.config;


import com.bottomline.autocomplete.entity.Name;
import com.bottomline.autocomplete.repository.NameRepository;
import com.bottomline.autocomplete.service.AutoCompleteService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    private final NameRepository nameRepository;
    private final AutoCompleteService autoCompleteService;

    public DataLoader(NameRepository nameRepository, AutoCompleteService autoCompleteService) {
        this.nameRepository = nameRepository;
        this.autoCompleteService = autoCompleteService;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Name> allNames = loadNamesFromFile("BoyNames.txt");

        // Extract unique name values using LinkedHashSet to maintain insertion order
        Set<String> uniqueNameValues = new LinkedHashSet<>();
        for (Name name : allNames) {
            uniqueNameValues.add(name.getNameValue());
        }

        // Create new Name objects from unique values
        List<Name> uniqueNames = new ArrayList<>();
        for (String nameValue : uniqueNameValues) {
            uniqueNames.add(new Name(nameValue));
        }

        nameRepository.saveAll(uniqueNames);
        uniqueNames.forEach(name -> autoCompleteService.insert(name.getNameValue()));
    }

    private List<Name> loadNamesFromFile(String filename) throws IOException {
        InputStream inputStream = new ClassPathResource(filename).getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        List<Name> names = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            String trimmed = line.trim();
            if (!trimmed.isEmpty()) {
                names.add(new Name(trimmed));
            }
        }
        return names;
    }
}

