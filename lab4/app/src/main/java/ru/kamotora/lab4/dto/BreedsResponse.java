package ru.kamotora.lab4.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BreedsResponse {
    /**
     * Список пород. Каждая порода содержит список под-пород, мы их не используем
     */
    private Map<String, List<String>> message;

    public List<String> getBreeds() {
        return Optional.ofNullable(message).
                map(Map::keySet)
                .map(ArrayList::new)
                .orElseGet(ArrayList::new);
    }
}
