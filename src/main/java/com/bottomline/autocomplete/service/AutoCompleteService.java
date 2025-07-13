package com.bottomline.autocomplete.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AutoCompleteService {

    private final TrieNode root;

    public AutoCompleteService() {
        this.root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode node = root;
        String lowerWord = word.toLowerCase();

        for (char c : lowerWord.toCharArray()) {
            node = node.children.computeIfAbsent(c, k -> new TrieNode());
            node.words.add(word);  // store original case
        }
        node.isEndOfWord = true;
    }

    public List<String> getSuggestions(String prefix) {
        if (prefix == null || prefix.isBlank()) return Collections.emptyList();

        TrieNode node = root;
        String lowerPrefix = prefix.toLowerCase();

        for (char c : lowerPrefix.toCharArray()) {
            node = node.children.get(c);
            if (node == null) return Collections.emptyList();
        }
        return node.words;
    }
}