package com.bottomline.autocomplete.service;

import com.bottomline.autocomplete.entity.Name;
import com.bottomline.autocomplete.repository.NameRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class AutoCompleteService {

    private final TrieNode root;
    private final NameRepository nameRepository;

    public AutoCompleteService(NameRepository nameRepository) {
        this.nameRepository = nameRepository;
        this.root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode node = root;
        String lowerWord = word.toLowerCase();

        for (char c : lowerWord.toCharArray()) {
            node = node.children.computeIfAbsent(c, k -> new TrieNode());
            node.words.add(word);
        }
        node.isEndOfWord = true;
    }

    public void processAndSaveNames(List<Name> names) {

        Set<String> uniqueNameValues = new LinkedHashSet<>();
        for (Name name : names) {
            uniqueNameValues.add(name.getNameValue());
        }

        List<Name> uniqueNames = uniqueNameValues.stream()
                .map(Name::new)
                .toList();

        nameRepository.saveAll(uniqueNames);
        uniqueNames.forEach(name -> insert(name.getNameValue()));
    }

    public List<String> getSuggestions(String prefix, int page, int size) {

        TrieNode node = root;
        String lowerPrefix = prefix.toLowerCase();

        for (char c : lowerPrefix.toCharArray()) {
            node = node.children.get(c);
            if (node == null) return Collections.emptyList();
        }

        List<String> allSuggestions = node.words;
        int start = page * size;
        int end = Math.min(start + size, allSuggestions.size());

        return (start < allSuggestions.size()) ?
                allSuggestions.subList(start, end) :
                Collections.emptyList();
    }
}