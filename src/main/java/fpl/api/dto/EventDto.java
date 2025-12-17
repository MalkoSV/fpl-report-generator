package fpl.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EventDto(
        int id,
        boolean isPrevious,
        boolean isCurrent
) {}
