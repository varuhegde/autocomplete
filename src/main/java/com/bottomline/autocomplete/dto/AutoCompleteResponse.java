package com.bottomline.autocomplete.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AutoCompleteResponse {
    private List<String> suggestions;
}