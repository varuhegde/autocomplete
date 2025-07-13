package com.bottomline.autocomplete.service;

import java.util.*;

public class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    List<String> words = new ArrayList<>();
    boolean isEndOfWord = false;
}
